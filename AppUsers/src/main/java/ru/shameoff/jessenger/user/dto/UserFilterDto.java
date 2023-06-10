package ru.shameoff.jessenger.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Поля, по которым можно фильтровать пользователей.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserFilterDto {

    @Schema(example = "Полное имя пользователя", description = "Полное имя пользователя или его часть")
    private String fullName;
    @Schema(example = "myCustomUsername", description = "Имя пользователя или его часть")
    private String username;
    @Schema(example = "myCustomCity", description = "Город пользователя")
    private String city;
    @Schema(example = "15", description = "Минимальный возраст пользователя")
    private Integer lowerAge;
    @Schema(example = "200", description = "Максимальный возраст пользователя")
    private Integer upperAge;

}
