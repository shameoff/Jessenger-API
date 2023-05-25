package ru.shameoff.jessenger.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shameoff.jessenger.chat.entity.ChatEntity;
import ru.shameoff.jessenger.chat.entity.ChatUserEntity;

import java.util.List;
import java.util.UUID;

public interface ChatUserRepository extends JpaRepository<ChatUserEntity, UUID> {
    List<ChatUserEntity> findAllByChatId(UUID chatId);

    @Query("SELECT cu.chat_id FROM chat_user cu WHERE cu.user_id = :userId1 AND cu.chat_id IN" +
    "(SELECT chat_id FROM chat_user cu2 WHERE cu2.user_id = :userId2)")
    ChatEntity findChatByUserIds(@Param("userId1") UUID userId1, @Param("userId2") UUID userId2);

}
