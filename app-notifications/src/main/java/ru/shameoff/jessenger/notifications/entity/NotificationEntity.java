package ru.shameoff.jessenger.notifications.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class NotificationEntity extends BaseEntity {
    @NonNull
    @Column(nullable = false)
    private String notificationType;
    @NonNull
    @Column(nullable = false)
    private String notificationText;
    @NonNull
    @Column(nullable = false)
    private UUID userId;
    @NonNull
    @Column(nullable = false)
    private String status;

    @Column
    private LocalDateTime read_at;

}

/*
Уведомление

Идентификатор уведомления
Тип уведомления
Текст уведомления
Идентификатор пользователя
Статус и дата-время прочтения
Дата-время получения
 */