package ru.shameoff.jessenger.friends.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockedDto {

    private String blockedId;
    private String blockedFullName;
    private LocalDateTime blocked_at;
}
