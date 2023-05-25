package ru.shameoff.jessenger.friends.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {

        private String friendId;
        private String friendFullName;
        private LocalDateTime added_at;
}
