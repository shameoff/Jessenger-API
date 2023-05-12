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
@Table(name = "blacklist")
public class BlacklistEntity {
    @Id
    @NonNull
    @Column(name = "id", columnDefinition = "VARCHAR(255)", nullable = false)
    private String id;
    @NonNull
    @Column(nullable = false)
    private String userId;
    @NonNull
    @Column(nullable = false)
    private String blockedUserId;
    @NonNull
    @Column(nullable = false)
    private String friendFullName;
    @Temporal(TemporalType.DATE)
    @Column
    private Date created_at;
    @Temporal(TemporalType.DATE)
    @Column
    private Date deleted_at;

}

/*
Блэклист

Дата добавления записи (заполняется автоматически)
Дата удаления записи (заполняется автоматически)
Идентификатор целевого пользователя (Который заносит в блеклист)
Идентификатор добавляемого пользователя
ФИО внешнего пользователя
 */