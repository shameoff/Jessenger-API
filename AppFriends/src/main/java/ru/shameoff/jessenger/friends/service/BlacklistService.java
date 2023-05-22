package ru.shameoff.jessenger.friends.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.common.security.JwtUserData;
import ru.shameoff.jessenger.friends.dto.AddFriendDto;
import ru.shameoff.jessenger.friends.repository.BlacklistRepository;
import ru.shameoff.jessenger.friends.repository.FriendsRepository;

import java.security.Security;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public void retrieveBlockedUsers() {
    }

    public ResponseEntity blockUser(UUID userId) {
        return null;
    }

    public ResponseEntity unblockUser(UUID userId) {
        return null;
    }

    public ResponseEntity updateBlockedUser(UUID userId) {
        return null;
    }

    public ResponseEntity searchBlockedUser(UUID userId) {
        return null;
    }

    public ResponseEntity isInBlacklist(UUID userToBlockId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUser = jwtData.getUsername();

        return null;
    }
}
