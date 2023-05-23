package ru.shameoff.jessenger.friends.controller;


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
    public ResponseEntity<?> getBlacklist() {
        return blacklistService.retrieveTargetUserBlacklist();
    }
    @GetMapping("/{userId}/check")
    public ResponseEntity<?> isInBlacklist(@PathVariable UUID userId) {
        return blacklistService.isInBlacklist(userId);
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
