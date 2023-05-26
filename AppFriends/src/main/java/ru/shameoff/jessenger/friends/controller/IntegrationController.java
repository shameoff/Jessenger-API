package ru.shameoff.jessenger.friends.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.friends.service.BlacklistService;
import ru.shameoff.jessenger.friends.service.FriendsService;

import java.util.UUID;

@RestController
@RequestMapping("/integration")
@RequiredArgsConstructor
public class IntegrationController {
    private final BlacklistService blacklistService;
    private final FriendsService friendsService;

    /**
     * Возвращает список UUIDов заблокированных пользователем пользователей
     * @param userId
     * @return
     */
    @GetMapping("/blacklist")
    public ResponseEntity<?> retrieveUserBlacklist(@RequestParam(required = true) UUID userId) {
        var result = blacklistService.retrieveUserBlacklist(userId);
        return ResponseEntity.ok(result);
    }

    /**
     * Проверяет, находится ли таргет юзер у внешнего юзера в чёрном списке
     *
     * @param targetUserId
     * @param foreignUserId
     * @return
     */
    @GetMapping("/is-blocked")
    public Boolean checkIfBlocked(@RequestParam(required = true) UUID targetUserId, @RequestParam(required = true) UUID foreignUserId) {
        var result = blacklistService.isBlocked(targetUserId, foreignUserId);
        return result;
    }

    /**
     * Возвращает список UUIDов друзей пользователя
     *
     * @param userId
     * @return
     */
    @GetMapping("/friends")
    public ResponseEntity<?> retrieveUserFriends(@RequestParam(required = true) UUID userId) {
        var result = friendsService.retrieveUserFriendsIds(userId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateInfo() {
        return null;
    }
}
