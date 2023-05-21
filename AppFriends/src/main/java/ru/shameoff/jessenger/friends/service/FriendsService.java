package ru.shameoff.jessenger.friends.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.friends.dto.AddFriendDto;
import ru.shameoff.jessenger.friends.dto.RetrieveFriendsDto;
import ru.shameoff.jessenger.friends.repository.FriendsRepository;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final FriendsRepository friendsRepository;

    public void retrieveFriends(RetrieveFriendsDto dto) {

    }

    public void retrieveFriendProfile() {
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
