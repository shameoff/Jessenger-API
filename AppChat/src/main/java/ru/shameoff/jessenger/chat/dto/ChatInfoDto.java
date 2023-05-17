package ru.shameoff.jessenger.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

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
public class ChatInfoDto {

        private String name;
        private UUID avatarUuid;
        private UUID adminId;
        private String creationDate;
}
