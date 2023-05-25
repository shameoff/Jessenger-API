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

    @GetMapping("/blacklist")
    public ResponseEntity<?> retrieveUserBlacklist(@RequestParam(required = true) UUID userId) {
        var result = blacklistService.retrieveUserBlacklist(userId);
        return ResponseEntity.ok(result);
    }


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
