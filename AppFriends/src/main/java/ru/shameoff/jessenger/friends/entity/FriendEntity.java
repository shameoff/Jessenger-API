package ru.shameoff.jessenger.friends.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
/*
Друзья

Идентификатор целевого пользователя (к которому добавляются друзья)
Идентификатор добавляемого пользователя
ФИО внешнего пользователя
Дата добавления друга (заполняется автоматически)
Дата удаления друга (заполняется автоматически)
 */