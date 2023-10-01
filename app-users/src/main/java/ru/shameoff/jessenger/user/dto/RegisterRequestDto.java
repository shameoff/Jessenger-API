package ru.shameoff.jessenger.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RegisterRequestDto {
    @NotBlank(message = "Я, конечно, понимаю, что ты никто и звать тебя никак, но тут назваться как-то нужно. Username заполни, а?")
    @Schema(example = "user")
    private final String username;
    @NotBlank(message = "А если нам придется слать тебе спам, куда мы будем это делать? Укажи email!")
    @Email(message = "Нормальный email укажи, да чё ты, отвечаю спам редко слать будем")
    @Schema(example = "mail@mail.com")
    private final String email;
    @NotBlank(message = "Ну и куда ты без пароля заходить собрался? С пустым паролем нельзя!")
    @Schema(example = "password")
    private final String password;
    @NotBlank(message = "Укажи полное имя пажалста, можешь прям из паспорта данные указать, если он твой, конечно")
    @Schema(example = "Ivan Ivanov")
    private final String fullName;
    @Past(message = "Чё, реально в будущем прям родился, да?")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "1990-01-01")
    private final LocalDate birthDate;
    @Schema(example = "+79999999999")
    private final String phoneNumber;
    @Schema(example = "Moscow")
    private final String city;
    @Schema(example = "zzzzzzzz-zzzz-zzzz-zzzz-zzzzzzzzzzzz")
    private final UUID avatarId;

}



