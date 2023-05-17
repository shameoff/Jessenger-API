package ru.shameoff.jessenger.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/*
Тело запроса

Идентификатор чата
Текст сообщения
Файлы вложений. Опциональное поле, пользователь имеет право его не указывать (или пустой массив)
 */

@Data
@AllArgsConstructor
public class NewMessageDto {

        private UUID receiverId;
        private UUID chatId;
        private String text;
        private String[] attachments;
}
