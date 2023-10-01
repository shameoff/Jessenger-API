package ru.shameoff.jessenger.friends.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendDto {
        private UUID friendId;
        private String friendFullName;
        private LocalDateTime added_at;
}
