package ru.shameoff.javalab1.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class RegisterDto {

    @NotNull
    private final String login;
    @NotNull
    private final String email;
    @NotNull
    private final String password;
    @NotNull
    private final String firstMiddleLastName;
    private final Date birthDate;
    private final String phoneNumber;
    private final String city;
    private final String avatarUuid;

}



