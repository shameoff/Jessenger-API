package ru.shameoff.jessenger.user.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @NonNull
    @Column(name = "id", columnDefinition = "VARCHAR(255)", nullable = false)
    private String id;
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
    private String avatarUuid;

}
