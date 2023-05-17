package ru.shameoff.jessenger.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shameoff.jessenger.chat.entity.ChatUserEntity;

import java.util.List;
import java.util.UUID;

public interface ChatUserRepository extends JpaRepository<ChatUserEntity, UUID> {
    List<ChatUserEntity> findAllByChatId(UUID chatId);
}
