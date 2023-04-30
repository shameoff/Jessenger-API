package ru.shameoff.javalab1.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    private String id;
    @NotNull
    private String login;
    @Email
    private String email;
    private String firstMiddleLastName;
    private Date birthDate;
    private String phoneNumber;
    private String city;
    private String avatarUuid;
}
