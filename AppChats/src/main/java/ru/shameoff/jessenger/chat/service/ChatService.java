package ru.shameoff.jessenger.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shameoff.jessenger.chat.dto.*;
import ru.shameoff.jessenger.chat.entity.ChatEntity;
import ru.shameoff.jessenger.chat.entity.ChatUserEntity;
import ru.shameoff.jessenger.chat.entity.MessageEntity;
import ru.shameoff.jessenger.chat.repository.ChatRepository;
import ru.shameoff.jessenger.chat.repository.ChatUserRepository;
import ru.shameoff.jessenger.chat.repository.MessageRepository;
import ru.shameoff.jessenger.common.client.FriendsServiceClient;
import ru.shameoff.jessenger.common.exception.BadRequestException;
import ru.shameoff.jessenger.common.exception.ForbiddenException;
import ru.shameoff.jessenger.common.exception.NotFoundException;
import ru.shameoff.jessenger.common.security.JwtUserData;
import ru.shameoff.jessenger.common.security.props.SecurityProps;
import ru.shameoff.jessenger.common.sharedDto.NewNotificationDto;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ModelMapper modelMapper;
    private final ChatRepository chatRepository;
    private final ChatUserRepository chatUserRepository;
    private final MessageRepository messageRepository;
    private final FriendsServiceClient friendsServiceClient;
    private final StreamBridge streamBridge;
    private final SecurityProps props;

    @Transactional
    public void sendMessage(NewMessageDto messageDto) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        ChatEntity chat = chatRepository.findById(messageDto.getChatId()).orElseThrow(() -> new NotFoundException("Чат не существует или пользователь в нем не состоит"));
        if (chat.getUsers().stream().noneMatch(u -> u.getId().equals(targetUserId))) {
            throw new NotFoundException("Чат не существует или пользователь в нем не состоит.");
        }
        MessageEntity message = MessageEntity.builder().messageText(messageDto.getText()).chatId(messageDto.getChatId()).build();
        messageRepository.save(message);
    }

    /**
     * От имени авторизованного пользователя отправляет сообщение пользователю, ID которого указан в messageDto.
     */
    @Transactional
    public void sendPrivateMessage(NewMessageDto messageDto) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        List<UUID> friendIds = friendsServiceClient.retrieveUserFriends(targetUserId, props.getIntegrationsProps().getApiKey());
        boolean isBlocked = friendsServiceClient.checkIfBlocked(targetUserId, messageDto.getReceiverId(), props.getIntegrationsProps().getApiKey());
        if (friendIds.stream().noneMatch(id -> id.equals(messageDto.getReceiverId()))) {
            throw new BadRequestException("Пользователь отсутствует в списке друзей");
        } else if (isBlocked) {
            throw new ForbiddenException("Внешний пользователь добавил пользователя в чёрный список");
        }
        var chatId = getPrivateChatId(targetUserId, messageDto.getReceiverId());
        var message = MessageEntity.builder().messageText(messageDto.getText()).chatId(chatId).build();
        message = messageRepository.save(message);
        var notification = NewNotificationDto.builder()
                .notificationType("NEW_MESSAGE")
                .notificationText("Пришло новое сообщение от пользователя " + targetUserId)
                .userId(messageDto.getReceiverId()).build();
        streamBridge.send("newNotificationEvent-out-0", notification);
    }

    /**
     * Пытается найти чат двух пользователей, если не находит, то создаёт его
     * @param targetUserId ID авторизованного пользователя
     * @param foreignUserId ID пользователя, с которым авторизованный пользователь хочет начать чат
     * @return UUID нового или существующего чата
     */
    @Transactional
    public UUID getPrivateChatId(UUID targetUserId, UUID foreignUserId) {
        var chat = chatUserRepository.findChatByUserIds(targetUserId, foreignUserId);
        if (chat == null) {
            chat = ChatEntity.builder().isPrivate(true).build();
            chat = chatRepository.save(chat);
            var chatUser1 = new ChatUserEntity(chat, targetUserId);
            chatUserRepository.save(chatUser1);
            var chatUser2 = new ChatUserEntity(chat, foreignUserId);
            chatUserRepository.save(chatUser2);
        }
        return chat.getId();
    }

    /**
     * Создаёт чат по запросу пользователя с указанными параметрами
     * @param chatDto DTO с параметрами чата
     * @return DTO с информацией о созданном чате
     */
    public ChatInfoDto createChat(ChatCreationDto chatDto) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        ChatEntity chat = ChatEntity.builder().name(chatDto.getName()).adminId(targetUserId).avatarId(chatDto.getAvatarId()).isPrivate(false).build();
        chatRepository.save(chat);
        chatUserRepository.save(ChatUserEntity.builder().chat(chat).userId(targetUserId).build());
        var chatUsers = new HashSet<UUID>(List.of(chatDto.getUsers()));
        chatUsers.add(targetUserId);
        for (UUID userId : chatUsers) {
            ChatUserEntity chatUser = ChatUserEntity.builder().chat(chat).userId(userId).build();
            chatUserRepository.save(chatUser);
        }
        return modelMapper.map(chat, ChatInfoDto.class);
    }

    /**
     * Изменяет чат по запросу пользователя с указанными параметрами
     * @param chatDto DTO с параметрами чата, которые нужно изменить
     * @return DTO с информацией об изменённом чате
     */
    public ChatInfoDto editChat(ChatChangeDto chatDto) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        ChatEntity chat = chatRepository.findChatEntityById(chatDto.getId());
        if (chat == null || chat.getAdminId() != targetUserId) {
            throw new NotFoundException("Чат не существует или пользователь не является его администратором");
        }
        chat.setName(chatDto.getName());
        chat.setAvatarId(chatDto.getAvatarId());
        chat = chatRepository.save(chat);
        return modelMapper.map(chat, ChatInfoDto.class);
    }

    /**
     * Выводит информацию о чате по запросу пользователя
     * @param chatId ID чата, информацию о котором надо получить
     * @return DTO с информацией о чате
     */
    public ChatInfoDto retrieveChatInformation(UUID chatId) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        ChatEntity chat = chatRepository.findChatEntityById(chatId);
        if (!chat.getUsers().contains(targetUserId)) {
            throw new NotFoundException("Чат не существует или пользователь в нем не состоит");
        }
        return modelMapper.map(chat, ChatInfoDto.class);
    }

    public List<MessageInListDto> retrieveChatMessages(UUID chatId) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        ChatEntity chat = chatRepository.findChatEntityById(chatId);
        if (!chat.getUsers().contains(targetUserId)) {
            throw new NotFoundException("Чат не существует или пользователь в нем не состоит");
        }
        List<MessageEntity> messages = messageRepository.findAllByChatId(chatId);
        return messages
                .stream().map(message -> modelMapper.map(message, MessageInListDto.class))
                .collect(Collectors.toList());
    }


    /**
     * Возвращает список чатов, в которых состоит авторизованный пользователь
     */
    public List<ChatInfoDto> retrieveChats() {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        return chatRepository.findUserChats(targetUserId)
                .stream().map(chatEntity -> modelMapper.map(chatEntity, ChatInfoDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Ищет сообщения по заданным параметрам
     */
    public List<MessageInListDto> findMessages(FindMessagesDto dto) {
        return null;
    }
}
