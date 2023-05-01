package ru.shameoff.javalab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

@Data
@AllArgsConstructor
public class EditUserInfoDto {

    private String fullName;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String phoneNumber;
    private String city;
    private String avatarUuid;
}
