package ru.shameoff.jessenger.common.sharedDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ProfileUpdateEventDto {

    private UUID userId;
    private String fullName;
}
