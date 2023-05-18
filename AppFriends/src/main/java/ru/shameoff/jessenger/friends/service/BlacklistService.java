package ru.shameoff.jessenger.friends.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.friends.dto.AddFriendDto;
import ru.shameoff.jessenger.friends.repository.BlacklistRepository;
import ru.shameoff.jessenger.friends.repository.FriendsRepository;

@Service
@RequiredArgsConstructor
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public void retrieveBlockedUsers() {
    }

    public ResponseEntity blockUser(AddFriendDto addFriendDto) {
        return null;
    }

    public ResponseEntity unblockUser(String userId) {
        return null;
    }

    public ResponseEntity updateBlockedUser(String userId) {
        return null;
    }

    public ResponseEntity searchBlockedUser(String userId) {
        return null;
    }
}
