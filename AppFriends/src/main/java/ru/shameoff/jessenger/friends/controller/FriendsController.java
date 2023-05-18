package ru.shameoff.jessenger.friends.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.friends.dto.AddFriendDto;
import ru.shameoff.jessenger.friends.service.FriendsService;

import javax.validation.Valid;

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
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;
    @PostMapping()
    public ResponseEntity retrieveFriends() {
        friendsService.retrieveFriends();
        return null;
    }

    @GetMapping("/{userId}")
    public ResponseEntity retrieveFriendsProfile(@PathVariable String userId) {
        friendsService.retrieveFriendsProfile();
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity addFriend(@Valid @RequestBody AddFriendDto addFriendDto) {


        return friendsService.addFriend(addFriendDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteFriend(@PathVariable String userId) {
        return friendsService.deleteFriend(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity updateFriend(@PathVariable String userId) {
        return friendsService.updateFriend(userId);
    }

    @PostMapping("/search")
    public ResponseEntity searchFriends(@RequestBody String userId) {
        return friendsService.searchFriends(userId);
    }

}
