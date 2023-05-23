package ru.shameoff.jessenger.chat.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "chat_user")
@NoArgsConstructor
public class ChatUserEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatEntity chat;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
}
