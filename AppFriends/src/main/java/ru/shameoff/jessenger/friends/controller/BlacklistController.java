package ru.shameoff.jessenger.friends.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shameoff.jessenger.friends.service.BlacklistService;

import java.util.UUID;


/*
Требуются все методы по аналогии с методами добавления в друзья
GET метод "Проверка нахождения в черном списке"

Метод должен принимать идентификатор внешнего пользователя и отвечать true/false в зависимости
от нахождения в черном списке
 */
@RestController
@RequestMapping("/blacklist")
@RequiredArgsConstructor
public class BlacklistController {

    private final BlacklistService blacklistService;
    @GetMapping("/{userId}/check")
    public void isInBlacklist(@PathVariable UUID userId) {

    }

    @GetMapping("/{userId}/add")
    public void addToBlacklist(@PathVariable UUID userId) {
           blacklistService.blockUser(userId);
    }

    @GetMapping("/{userId}/delete")
    public void deleteFromBlacklist(@PathVariable UUID userId) {
        blacklistService.unblockUser(userId);
    }


}
