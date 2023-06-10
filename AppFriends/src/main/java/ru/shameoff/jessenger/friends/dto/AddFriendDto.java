package ru.shameoff.jessenger.friends.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/*
Должен принимать в теле запроса необходимые поля для добавления друга, все поля кроме дат должны быть
обязательными к заполнению
При обработке запроса, перед сохранением в БД, необходимо заполнить поле даты добавления друга
При обработке запроса, необходимо совершить проверку на то, что пользователь уже не добавлен в список, например по
идентификаторам целевого и внешнего пользователя. В случае, если такая пара уже есть, но друг "удален", необходимо
занулить дату удаления данной связки и при необходимости актуализировать данные (например, ФИО)
 */
@Data
@AllArgsConstructor
public class AddFriendDto {
    @Schema(example = "uuiduuid-uuid-uuid-uuid-uuiduuuuiduu")
    private UUID friendId;
    @Schema(example = "Example Name")
    private String friendFullName;
}
