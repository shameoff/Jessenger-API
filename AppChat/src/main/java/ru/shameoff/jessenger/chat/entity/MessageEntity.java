package ru.shameoff.jessenger.chat.entity;

import lombok.*;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "messages")
public class MessageEntity extends BaseEntity {

        @NonNull
        @Column(nullable = false)
        private UUID chatId;
        @NonNull
        @Column(nullable = false)
        private String messageText;
}