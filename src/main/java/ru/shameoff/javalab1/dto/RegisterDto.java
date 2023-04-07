package ru.shameoff.javalab1.dto;

import lombok.*;

import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NonNull
    private String login;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String firstMiddleLastName;

    private Date birthDate;
    private String phoneNumber;
    private String city;
    private String avatarUuid;
}



