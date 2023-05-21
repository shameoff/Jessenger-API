package ru.shameoff.jessenger.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class LoginDto {
    @NotNull
    @Schema(example = "user")
    private final String username;
    @NotNull
    @Schema(example = "password")
    private final String password;
}
