package ru.shameoff.jessenger.notifications.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @NonNull
    @Column(name = "id", columnDefinition = "VARCHAR(255)", nullable = false)
    private String id;
    @NonNull
    @Column(nullable = false)
    private String notificationType;
    @NonNull
    @Column(nullable = false)
    private String notificationText;
    @NonNull
    @Column(nullable = false)
    private String userId;
    @NonNull
    @Column(nullable = false)
    private String status;

    @Temporal(TemporalType.DATE)
    @Column
    private Date read_at;
    @Temporal(TemporalType.DATE)
    @Column
    private Date received_at;

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