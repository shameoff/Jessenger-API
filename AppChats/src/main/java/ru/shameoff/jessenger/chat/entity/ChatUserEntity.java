package ru.shameoff.jessenger.chat.entity;
import lombok.*;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "chat_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatEntity chat;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
}
