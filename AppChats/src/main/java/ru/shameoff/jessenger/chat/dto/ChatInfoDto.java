package ru.shameoff.jessenger.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/*
Тело ответа

Наименование чата (если диалог - имя собеседника)
Аватарка чата (если не диалог)
Администратор чата - идентификатор пользователя (если не диалог)
Дата создания чата
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatInfoDto {
        private UUID id;
        private String name;
        private UUID avatarId;
        private UUID adminId;
        private UUID[] users;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;
}
