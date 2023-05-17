package ru.shameoff.jessenger.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shameoff.jessenger.chat.entity.MessageEntity;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    List<MessageEntity> findAllByChatId(UUID chatId);
}
