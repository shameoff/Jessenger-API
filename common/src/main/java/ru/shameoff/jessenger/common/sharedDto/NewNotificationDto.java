package ru.shameoff.jessenger.common.sharedDto;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/*
Идентификатор пользователя (uuid/login)
Тип уведомления
Это поле означает какое именно уведомление пришло. Например, уведомление о добавлении в друзья
Текст уведомления
 */

/**
 * Используется для отправки уведомлений в сервис уведомлений
 */
@Data
@Builder
public class NewNotificationDto {

    public UUID userId;
    public String notificationType;
    public String notificationText;
}
