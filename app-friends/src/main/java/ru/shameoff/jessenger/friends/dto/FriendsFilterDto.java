package ru.shameoff.jessenger.friends.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendsFilterDto {
    private String friendFullName;
    private LocalDateTime added_at;
}
