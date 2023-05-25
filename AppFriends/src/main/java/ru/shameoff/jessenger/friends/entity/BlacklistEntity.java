package ru.shameoff.jessenger.friends.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blacklist")
public class BlacklistEntity extends BaseEntity {
    @NonNull
    @Column(nullable = false)
    private UUID userId;
    @NonNull
    @Column(nullable = false)
    private UUID blockedId;
    @NonNull
    @Column(nullable = false)
    private String blockedFullName;
}

/*
Блэклист

Дата добавления записи (заполняется автоматически)
Дата удаления записи (заполняется автоматически)
Идентификатор целевого пользователя (Который заносит в блеклист)
Идентификатор добавляемого пользователя
ФИО внешнего пользователя
 */