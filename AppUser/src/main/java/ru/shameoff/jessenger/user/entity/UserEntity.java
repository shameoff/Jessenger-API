package ru.shameoff.jessenger.user.entity;

import lombok.*;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @NonNull
    @Column(nullable = false)
    private String username;
     private String email;
    @NonNull
    @Column(nullable = false)
    private String password;
    @NonNull
    @Column(nullable = false)
    private String fullName;
    @Temporal(TemporalType.DATE)
    @Column
    private Date birthDate;
    @Column
    private String phoneNumber;
    @Column
    private String city;
    @Column
    private UUID avatarUuid;

}
