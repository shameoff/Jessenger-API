package ru.shameoff.jessenger.chat.dto;


/*
Тело запроса

Идентификатор изменяемого чата
Наименование
Аватарка - файл изображения
Список пользователей-участников (кроме себя - текущий пользователь подставится автоматически)
 */

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ChatChangeDto {

    private UUID id;
    private String name;
    private UUID avatarId;
}
