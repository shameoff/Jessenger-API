package ru.shameoff.jessenger.chat.dto;

import java.util.UUID;

/*
Тело ответа, массив

Идентификатор сообщения
Дата отправки сообщения
Текст сообщения
Имя отправителя
Аватарка отправителя - id файла аватарки
Массив файлов:

Идентификатор файла
Наименование файла
Размер файла
 */
public class MessageInListDto {
    private UUID id;
    private String date;
    private String text;
    private String senderName;
    private UUID senderAvatarUuid;
    private AttachmentDto[] attachments;
}
