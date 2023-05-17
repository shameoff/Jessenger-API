package ru.shameoff.jessenger.chat.dto;

import java.util.UUID;

/*
Массив файлов:

Идентификатор файла
Наименование файла
Размер файла
 */
public class AttachmentDto {
    private UUID id;
    private String name;
    private String size;
}
