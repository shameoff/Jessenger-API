package ru.shameoff.javalab1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(255)", nullable = false)
    private String id;
    @Column
    private String login;
    @Column
    private String firstName;
    @Column
    private String middleName;
    @Column
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column
    private Date birthDate;

}
