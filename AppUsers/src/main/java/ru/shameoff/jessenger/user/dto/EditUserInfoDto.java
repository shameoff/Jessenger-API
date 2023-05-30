package ru.shameoff.jessenger.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EditUserInfoDto {

    @Schema(example = "Ivanov Ivan Ivanonich")
    private String fullName;
    @Past(message = "И давно у нас люди в будушем рождаются?")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @Schema(example = "88005553535")
    private String phoneNumber;
    @Schema(example = "New York")
    private String city;
    @Schema(example = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
    private UUID avatarId;
}
