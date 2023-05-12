package ru.shameoff.jessenger.chat.entity;

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
@Table(name = "chats")
public class ChatEntity {
        @Id
        @NonNull
        @Column(name = "id", columnDefinition = "VARCHAR(255)", nullable = false)
        private String id;

        @NonNull
        @Column(nullable = false)
        private Boolean isDialog;
        @Column
        private String adminId;

        @NonNull
        @Temporal(TemporalType.DATE)
        @Column(nullable = false)
        private Date creationDate;
        @Column
        private String avatarUuid;
}

/*
Чат (ниже по тексту ещё название - переписка)

Идентификатор (задаётся автоматически)
Тип чата (Диалог/Чат)
Наименование (только для чатов)
Админ - идентификатор пользователя (только для чатов)
Дата создания (только для чатов)
Аватарка - идентификатор файла (только для чатов)
 */