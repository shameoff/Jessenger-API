package ru.shameoff.jessenger.friends.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shameoff.jessenger.friends.dto.AddFriendDto;
import ru.shameoff.jessenger.friends.repository.FriendsRepository;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final FriendsRepository friendsRepository;

    public void retrieveFriends() {
    }

    public void retrieveFriendsProfile() {
    }

    public ResponseEntity addFriend(AddFriendDto addFriendDto) {
        return null;
    }

    public ResponseEntity deleteFriend(String userId) {
        return null;
    }

    public ResponseEntity updateFriend(String userId) {
        return null;
    }

    public ResponseEntity searchFriends(String userId) {
        return null;
    }
}
