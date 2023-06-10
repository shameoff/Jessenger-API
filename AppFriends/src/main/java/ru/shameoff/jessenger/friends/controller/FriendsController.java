package ru.shameoff.jessenger.friends.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.friends.dto.AddFriendDto;
import ru.shameoff.jessenger.friends.dto.FriendDto;
import ru.shameoff.jessenger.friends.dto.RetrieveFriendsRequest;
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
    @Operation(summary = "Получение списка друзей авторизованного пользователя по указанным параметрам",
            description = "Также прямо заменяет эндпоинт поиска, так как поиск осуществляется по фильтрам данного запроса")
    public Page<FriendDto> retrieveFriends(@RequestBody RetrieveFriendsRequest dto) {
        return friendsService.retrieveUserFriends(dto);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получение профиля друга по его идентификатору")
    public FriendDto retrieveFriendProfile(@PathVariable UUID userId) {
        return friendsService.retrieveFriendProfile(userId);
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление в друзья внешнего пользователя")
    public void addFriend(@Valid @RequestBody AddFriendDto addFriendDto) {
        friendsService.addFriend(addFriendDto);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Удаление внешнего пользователя из друзей")
    public void deleteFriend(@PathVariable UUID userId) {
        friendsService.deleteFriend(userId);
    }


//    @PatchMapping("/{userId}")
//    public ResponseEntity<?> updateFriend(@PathVariable UUID userId) {
//        return friendsService.updateFriend(userId);
//
//    }

}
