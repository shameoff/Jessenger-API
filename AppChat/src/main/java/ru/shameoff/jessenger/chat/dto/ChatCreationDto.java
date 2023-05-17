package ru.shameoff.jessenger.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/*
Тело запроса

Наименование
Аватарка - файл изображения
Список пользователей-участников (кроме себя - текущий пользователь подставится автоматически)
 */
@Data
@AllArgsConstructor
public class ChatCreationDto {

        private String name;
        private String avatarUuid;
        private UUID[] users;
}
