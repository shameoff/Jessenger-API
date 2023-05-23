package ru.shameoff.jessenger.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class ChatEntity extends BaseEntity {
        @NonNull
        @Column(nullable = false)
        private Boolean isPrivate;
        @Column
        private UUID adminId;

        @Column
        private UUID avatarId;

        @Column
        private String name;

        @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<ChatUserEntity> users;
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