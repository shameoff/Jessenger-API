package ru.shameoff.jessenger.friends.entity;

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
@Table(name = "friends")
public class FriendEntity extends BaseEntity {

    @NonNull
    @Column(nullable = false)
    private UUID userId;
    @NonNull
    @Column(nullable = false)
    private UUID friendId;
    @NonNull
    @Column(nullable = false)
    private String friendFullName;
}