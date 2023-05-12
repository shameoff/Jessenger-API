package ru.shameoff.jessenger.friends.entity;

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
@Table(name = "friends")
public class FriendEntity {

    @Id
    @NonNull
    @Column(name = "id", columnDefinition = "VARCHAR(255)", nullable = false)
    private String id;
    @NonNull
    @Column(nullable = false)
    private String userId;
    @NonNull
    @Column(nullable = false)
    private String friendId;
    @NonNull
    @Column(nullable = false)
    private String friendFullName;
    @Temporal(TemporalType.DATE)
    @Column
    private Date added_at;
    @Temporal(TemporalType.DATE)
    @Column
    private Date deleted_at;
}
/*
Друзья

Идентификатор целевого пользователя (к которому добавляются друзья)
Идентификатор добавляемого пользователя
ФИО внешнего пользователя
Дата добавления друга (заполняется автоматически)
Дата удаления друга (заполняется автоматически)
 */