package ru.shameoff.jessenger.friends.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.friends.dto.AddFriendDto;
import ru.shameoff.jessenger.friends.dto.RetrieveFriendsDto;
import ru.shameoff.jessenger.friends.service.FriendsService;

import javax.validation.Valid;
import java.util.UUID;

/*
PATCH метод: Синхронизация данных

Необходим для синхронизации данных с внешними сервисами
Должен принимать идентификатор друга, находить все записи, где он участвует и обновлять поле ФИО. Данная функция в
дальнейшем так же будет использоваться при "правильной"
синхронизации через рэббит.



DELETE метод "Удалить друга"
При успешном исходе выполнения метода должно отправляться уведомление тому, кого текущий пользователь удаляет
из друзей.

Принимает идентификатор: внешний пользователь
Удаляет из списка друзей, заполняя поле "дата удаления"



POST метод "Поиск"

Принимает в теле запроса набор параметров из сущности друзья и осуществляет поиск по произвольному набору этих
параметров. Как пример, мы решили искать по дате добавления, присылаем только эту самую дату и метод должен
осуществить запрос в БД только по этому полю, полностью игнорируя остальные
Ответ должен быть с пагинацией, по аналогии с методом "Список друзей"
 */
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FriendsController {

    private final FriendsService friendsService;
    @PostMapping("")
    @Operation(summary = "Получение списка друзей авторизованного пользователя по указанным параметрам")
    public ResponseEntity<?> retrieveFriends(@RequestBody RetrieveFriendsDto dto) {
        return friendsService.retrieveUserFriends(dto);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получение профиля друга по его идентификатору")
    public ResponseEntity<?> retrieveFriendProfile(@PathVariable UUID userId) {
        friendsService.retrieveFriendProfile(userId);
        return null;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление в друзья внешнего пользователя")
    public ResponseEntity<?> addFriend(@Valid @RequestBody AddFriendDto addFriendDto) {
        return friendsService.addFriend(addFriendDto);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Удаление внешнего пользователя из друзей")
    public ResponseEntity<?> deleteFriend(@PathVariable UUID userId) {
        return friendsService.deleteFriend(userId);
    }

    @PostMapping("/search")
    @Operation(summary = "Поиск друзей по указанным параметрам")
    public ResponseEntity<?> searchFriends(@RequestBody UUID userId) {
        return friendsService.searchFriends(userId);
    }


//    @PatchMapping("/{userId}")
//    public ResponseEntity<?> updateFriend(@PathVariable UUID userId) {
//        return friendsService.updateFriend(userId);
//
//    }

}
