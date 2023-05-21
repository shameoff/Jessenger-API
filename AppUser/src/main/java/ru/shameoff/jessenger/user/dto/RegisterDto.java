package ru.shameoff.jessenger.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RegisterDto {

    @NotNull
    @Schema(example = "user")
    private final String username;
    @NotNull
    @Schema(example = "mail@mail.com")
    private final String email;
    @NotNull
    @Schema(example = "password")
    private final String password;
    @NotNull
    @Schema(example = "Ivan Ivanov")
    private final String fullName;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "1990-01-01")
    private final Date birthDate;
    @Schema(example = "+79999999999")
    private final String phoneNumber;
    @Schema(example = "Moscow")
    private final String city;
    private final UUID avatarUuid;

}



