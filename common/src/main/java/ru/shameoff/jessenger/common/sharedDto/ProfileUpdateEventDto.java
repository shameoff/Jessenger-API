package ru.shameoff.jessenger.common.sharedDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


/**
 * Используется для обновления профилей в разных микросервисах при обновлении в UserService
 */
@Data
@NoArgsConstructor
public class ProfileUpdateEventDto {

    private UUID userId;
    private String fullName;
}
