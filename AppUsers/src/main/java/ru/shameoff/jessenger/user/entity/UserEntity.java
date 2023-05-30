package ru.shameoff.jessenger.user.entity;

import lombok.*;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @NonNull
    @Column(nullable = false)
    private String username;
    @NonNull
    @Column(nullable = false)
     private String email;
    @NonNull
    @Column(nullable = false)
    private String password;
    @NonNull
    @Column(nullable = false)
    private String fullName;
    @Column
    private LocalDate birthDate;
    @Column
    private String phoneNumber;
    @Column
    private String city;
    @Column
    private UUID avatarId;
}
