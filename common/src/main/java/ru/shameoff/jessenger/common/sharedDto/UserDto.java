package ru.shameoff.jessenger.common.sharedDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
import java.util.UUID;

/**
 * DTO для передачи данных пользоватлея между сервисами
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    @NonNull
    @Schema(example = "abcdefgh-ijkl-mnop-qrst-uvwxyz123456")
    private UUID id;
    @NonNull
    @Schema(example = "default")
    private String username;
    @NonNull
    @Schema(example = "default@example.com")
    private String email;
    @NonNull
    @Schema(example = "Example Name")
    private String fullName;
    @Schema(example = "2003-16-03")
    private Date birthDate;
    @Schema(example = "88005553535")
    private String phoneNumber;
    @Schema(example = "Tomsk")
    private String city;
    @Schema(example = "654321zy-xwvu-tsrq-pomn-lkjihgfedcba")
    private UUID avatarId;
}
