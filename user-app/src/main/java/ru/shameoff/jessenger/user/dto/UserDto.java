package ru.shameoff.jessenger.user.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String id;
    private String username;
    private String email;
    private String fullName;
    private Date birthDate;
    private String phoneNumber;
    private String city;
    private String avatarUuid;
}
