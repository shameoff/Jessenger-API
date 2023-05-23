package ru.shameoff.jessenger.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class MessageEntity extends BaseEntity {

        @NonNull
        @Column(nullable = false)
        private UUID chatId;
        @NonNull
        @Column(nullable = false)
        private String messageText;
}

/*
Сообщение

Идентификатор (задаётся автоматически)
Ссылка на чат
Дата отправки
Текст сообщения (не длиннее 500 символов)
 */
