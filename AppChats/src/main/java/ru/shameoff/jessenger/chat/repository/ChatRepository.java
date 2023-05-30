package ru.shameoff.jessenger.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.shameoff.jessenger.chat.entity.ChatEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {

    ChatEntity findChatEntityById(UUID uuid);

    @Query("SELECT cu.chat FROM ChatUserEntity cu WHERE cu.userId = :userId")
    List<ChatEntity> findUserChats(UUID userId);
}
