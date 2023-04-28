package ru.shameoff.javalab1.dto;

import lombok.*;

@Data
@RequiredArgsConstructor
public class LoginDto {
    @NonNull
    private String login;
    @NonNull
    private String password;
}
