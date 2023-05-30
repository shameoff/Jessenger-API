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

    // TODO отправление сообщений через streamBridge
    @Transactional
    public ResponseEntity sendMessage(NewMessageDto messageDto) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        try {
            ChatEntity chat = chatRepository.findById(messageDto.getChatId()).orElseThrow(RuntimeException::new);
            if (chat.getUsers().stream().noneMatch(u -> u.getId().equals(targetUserId))) {
                return ResponseEntity.badRequest().body("Чат не существует или пользователь в нем не состоит.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Чат не существует или пользователь в нем не состоит.");
        }
        MessageEntity message = MessageEntity.builder().messageText(messageDto.getText()).chatId(messageDto.getChatId()).build();
        messageRepository.save(message);
        return ResponseEntity.ok().build();
    }

    /**
     * От имени авторизованного пользователя отправляет сообщение пользователю, ID которого указан в messageDto.
     *
     * @param messageDto
     * @return
     */
    @Transactional
    public ResponseEntity<?> sendPrivateMessage(NewMessageDto messageDto) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        List<UUID> friendIds = friendsServiceClient.retrieveUserFriends(targetUserId, props.getIntegrationsProps().getApiKey());
        boolean isBlocked = friendsServiceClient.checkIfBlocked(targetUserId, messageDto.getReceiverId(), props.getIntegrationsProps().getApiKey());
        if (friendIds.stream().noneMatch(id -> id.equals(messageDto.getReceiverId()))) {
            return ResponseEntity.badRequest().body("Пользователь отсутствует в списке друзей");
        } else if (isBlocked) {
            return ResponseEntity.status(403).body("Пользователь добавил вас в чёрный список");
        }
        var chatId = getPrivateChatId(targetUserId, messageDto.getReceiverId());
        var message = MessageEntity.builder().messageText(messageDto.getText()).chatId(chatId).build();

        message = messageRepository.save(message);
        var notification = NewNotificationDto.builder().notificationType("NEW_MESSAGE").notificationText("Пришло новое сообщение от пользователя " + targetUserId).userId(messageDto.getReceiverId()).build();
        streamBridge.send("newNotificationEvent-out-0", notification);
        return ResponseEntity.ok().body("Сообщение отправлено");
    }

    /**
     * Пытается найти чат двух пользователей, если не находит, то создаёт его
     *
     * @param targetUserId
     * @param foreignUserId
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

    public ResponseEntity createChat(ChatCreationDto chatDto) {
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
        return ResponseEntity.ok().body(modelMapper.map(chat, ChatInfoDto.class));
    }


    public ResponseEntity editChat(ChatChangeDto chatDto) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        ChatEntity chat = chatRepository.findChatEntityById(chatDto.getId());
        if (chat == null || chat.getAdminId() != targetUserId) {
            return ResponseEntity.notFound().build();
        }
        chat.setName(chatDto.getName());
        chat.setAvatarId(chatDto.getAvatarId());
        return ResponseEntity.ok().body(modelMapper.map(chat, ChatInfoDto.class));
    }

    public ResponseEntity retrieveChatInformation(UUID chatId) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        ChatEntity chat = chatRepository.findChatEntityById(chatId);
        if (!chat.getUsers().contains(targetUserId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(modelMapper.map(chat, ChatInfoDto.class));
    }

    public ResponseEntity retrieveChatMessages(UUID chatId) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        ChatEntity chat = chatRepository.findChatEntityById(chatId);
        if (!chat.getUsers().contains(targetUserId)) {
            return ResponseEntity.notFound().build();
        }
        List<MessageEntity> messages = messageRepository.findAllByChatId(chatId);
        List<MessageInListDto> messageDtos = messages
                .stream().map(message -> modelMapper.map(message, MessageInListDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(messageDtos);
    }


    public ResponseEntity retrieveChats() {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        List<ChatInfoDto> chats = chatRepository.findUserChats(targetUserId)
                .stream().map(chatEntity -> modelMapper.map(chatEntity, ChatInfoDto.class))
                .collect(Collectors.toList());
        log.info("Чаты здесь = {}, а userId = {}", chats, targetUserId);
        return ResponseEntity.ok().body(chats);
    }

    public ResponseEntity findMessages() {
        return ResponseEntity.ok().build();
    }
}
