package ru.shameoff.jessenger.user.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class LoginDto {
    @NotNull
    private final String username;
    @NotNull
    private final String password;
}
