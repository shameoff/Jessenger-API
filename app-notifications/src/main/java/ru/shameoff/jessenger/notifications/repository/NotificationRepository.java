package ru.shameoff.jessenger.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.shameoff.jessenger.notifications.entity.NotificationEntity;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    Integer countAllByStatus(String status);
    @Modifying
    @Query("UPDATE NotificationEntity n SET n.status = :status WHERE n.id IN :ids")
    void updateNotificationStatusByIds(List<UUID> ids, String status);
}
