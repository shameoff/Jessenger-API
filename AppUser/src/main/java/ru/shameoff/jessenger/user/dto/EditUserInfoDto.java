package ru.shameoff.jessenger.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EditUserInfoDto {

    private String fullName;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String phoneNumber;
    private String city;
    private UUID avatarUuid;
}
