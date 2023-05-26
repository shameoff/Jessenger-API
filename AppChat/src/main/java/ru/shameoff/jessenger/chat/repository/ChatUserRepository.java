package ru.shameoff.jessenger.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shameoff.jessenger.chat.entity.ChatEntity;
import ru.shameoff.jessenger.chat.entity.ChatUserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUserEntity, UUID> {
    List<ChatUserEntity> findAllByChatId(UUID chatId);

    @Query("SELECT cu.chat FROM ChatUserEntity cu WHERE cu.userId = :userId1 AND cu.chat IN" +
    "(SELECT cu2.chat FROM ChatUserEntity cu2 WHERE cu2.userId = :userId2)")
    ChatEntity findChatByUserIds(@Param("userId1") UUID userId1, @Param("userId2") UUID userId2);

}
