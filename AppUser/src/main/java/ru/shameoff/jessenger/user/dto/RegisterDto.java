package ru.shameoff.jessenger.user.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@AllArgsConstructor
public class RegisterDto {

    @NotNull
    private final String username;
    @NotNull
    private final String email;
    @NotNull
    private final String password;
    @NotNull
    private final String fullName;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final Date birthDate;
    private final String phoneNumber;
    private final String city;
    private final String avatarUuid;

}



