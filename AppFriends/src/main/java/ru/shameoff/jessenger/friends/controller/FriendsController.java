package ru.shameoff.jessenger.friends.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.friends.service.FriendsService;

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


}
