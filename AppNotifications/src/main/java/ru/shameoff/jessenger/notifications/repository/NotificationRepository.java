package ru.shameoff.jessenger.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shameoff.jessenger.notifications.entity.NotificationEntity;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
}
