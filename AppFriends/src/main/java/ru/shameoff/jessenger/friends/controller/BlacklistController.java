package ru.shameoff.jessenger.friends.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.friends.service.BlacklistService;

import java.util.UUID;


/*
Требуются все методы по аналогии с методами добавления в друзья
GET метод "Проверка нахождения в черном списке"

Метод должен принимать идентификатор внешнего пользователя и отвечать true/false в зависимости
от нахождения в черном списке
 */
@RestController
@RequestMapping("/api/blacklist")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BlacklistController {

    private final BlacklistService blacklistService;

    @GetMapping("")
    @Operation(summary = "Получение черного списка авторизованного пользователя")
    public ResponseEntity<?> retrieveBlacklist() {
        return blacklistService.retrieveUserBlacklist();
    }
    @GetMapping("/{userId}/check")
    @Operation(summary = "Проверка на то, находится ли авторизованный пользователь у внешнего в чёрном списке")
    public ResponseEntity<?> isInBlacklist(@PathVariable UUID userId) {
        return blacklistService.isBlocked(userId);
    }

    @GetMapping("/{userId}/add")
    @Operation(summary = "Добавление в черный список внешнего пользователя")
    public void addToBlacklist(@PathVariable UUID userId) {
           blacklistService.blockUser(userId);
    }

    @GetMapping("/{userId}/delete")
    @Operation(summary = "Удаление из черного списка внешнего пользователя")
    public void deleteFromBlacklist(@PathVariable UUID userId) {
        blacklistService.unblockUser(userId);
    }

}
