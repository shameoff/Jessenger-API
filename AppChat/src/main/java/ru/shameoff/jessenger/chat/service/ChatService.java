package ru.shameoff.jessenger.chat.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.chat.dto.ChatChangeDto;
import ru.shameoff.jessenger.chat.dto.ChatCreationDto;
import ru.shameoff.jessenger.chat.dto.MessageInListDto;
import ru.shameoff.jessenger.chat.dto.NewMessageDto;
import ru.shameoff.jessenger.chat.entity.ChatEntity;
import ru.shameoff.jessenger.chat.entity.ChatUserEntity;
import ru.shameoff.jessenger.chat.entity.MessageEntity;
import ru.shameoff.jessenger.chat.repository.ChatRepository;
import ru.shameoff.jessenger.chat.repository.ChatUserRepository;
import ru.shameoff.jessenger.chat.repository.MessageRepository;
import ru.shameoff.jessenger.common.security.JwtUserData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ModelMapper modelMapper;
    private final ChatRepository chatRepository;
    private final ChatUserRepository chatUserRepository;
    private final MessageRepository messageRepository;
    private final StreamBridge streamBridge;

    // TODO отправление сообщений через streamBridge
    public ResponseEntity sendMessage(NewMessageDto messageDto) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        if (messageDto.getChatId() != null) {
            ChatEntity chat = chatRepository.findById(messageDto.getChatId()).orElseThrow(RuntimeException::new);
            if (chat.getUsers().stream().noneMatch(u -> u.getId().equals(me))) {
                return ResponseEntity.badRequest().body("Пользователь не состоит в чате");
            }
            MessageEntity message = modelMapper.map(messageDto, MessageEntity.class);
            messageRepository.save(message);
            return ResponseEntity.ok().build();
        } else if (messageDto.getReceiverId() != null) {

        }
        MessageEntity message = modelMapper.map(messageDto, MessageEntity.class);
        messageRepository.save(message);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity createChat(ChatCreationDto chatDto) {
        var jwt = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwt.getId();
        ChatEntity chat = modelMapper.map(chatDto, ChatEntity.class);
        chat.setAdminId(targetUserId);
        chat.setIsPrivate(false);
        chatRepository.save(chat);
        for (UUID userId : chatDto.getUsers()) {
            ChatUserEntity chatUser = ChatUserEntity.builder()
                    .chat(chat)
                    .userId(userId)
                    .build();
            chatUserRepository.save(chatUser);
        }
        chatUserRepository.save(ChatUserEntity.builder().chat(chat).userId(targetUserId).build());
        return ResponseEntity.ok().body(chat.getId());
    }


    public ResponseEntity editChat(ChatChangeDto chatDto) {
        ChatEntity chat = chatRepository.findById(chatDto.getId()).orElseThrow(RuntimeException::new);
        chat.setName(chatDto.getName());
        chat.setAvatarId(chatDto.getAvatarId());
        for (UUID userId : chatDto.getUsers()) {
            ChatUserEntity chatUser = new ChatUserEntity();
            chatUser.setChat(chat);
            chatUser.setUserId(userId);
            chatUserRepository.save(chatUser);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity retrieveChatInformation(UUID chatId) {
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow(RuntimeException::new);
        chat.setUsers(chatUserRepository.findAllByChatId(chatId));
        return ResponseEntity.ok().body(modelMapper.map(chat, ChatCreationDto.class));
    }

    public ResponseEntity retrieveChatMessages(UUID chatId) {
        List<MessageEntity> messages = messageRepository.findAllByChatId(chatId);
        List<MessageInListDto> messageDtos = messages.stream()
                .map(message -> modelMapper.map(message, MessageInListDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(messageDtos);
    }


    public ResponseEntity retrieveChats() {
        return ResponseEntity.ok().build();
    }

    public ResponseEntity findMessages() {
        return ResponseEntity.ok().build();
    }
}
