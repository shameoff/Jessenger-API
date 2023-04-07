package ru.shameoff.javalab1.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {

    @NonNull
    private String id;
    @NonNull
    private String login;
    @NonNull
    private String email;
    @NonNull
    private String firstMiddleLastName;
    private Date birthDate;
    private String phoneNumber;
    private String city;
    private String avatarUuid;
}
