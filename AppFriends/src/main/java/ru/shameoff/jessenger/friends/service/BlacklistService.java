package ru.shameoff.jessenger.friends.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.common.client.UserServiceClient;
import ru.shameoff.jessenger.common.security.JwtUserData;
import ru.shameoff.jessenger.common.security.props.SecurityProps;
import ru.shameoff.jessenger.common.sharedDto.NewNotificationDto;
import ru.shameoff.jessenger.friends.entity.BlacklistEntity;
import ru.shameoff.jessenger.friends.repository.BlacklistRepository;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;
    private final UserServiceClient userServiceClient;
    private final SecurityProps props;
    private final StreamBridge streamBridge;

    public ResponseEntity<?> retrieveTargetUserBlacklist() {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var blockedUsers = blacklistRepository.findAllByUserId(targetUserId);
        return ResponseEntity.ok().body(blockedUsers);
    }

    public ResponseEntity<?> blockUser(UUID foreignUserId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var foreignUser = userServiceClient.getUserById(foreignUserId, props.getIntegrationsProps().getApiKey());
        var newEntry = new BlacklistEntity();
        newEntry.setUserId(targetUserId);
        newEntry.setBlockedUserId(foreignUserId);
        newEntry.setBlockedFullName(foreignUser.getFullName());
        var notification = new NewNotificationDto();
        notification.setNotificationText("Пользователь " + newEntry.getBlockedFullName() + " был добавлен в чёрный список");
        notification.setUserId(targetUserId);
        notification.setNotificationType("USER_BLOCKED");
        var blocked = blacklistRepository.save(newEntry);
        streamBridge.send("newNotificationEvent-out-0", notification);
        return ResponseEntity.ok().body(blocked);
    }

    public ResponseEntity<?> unblockUser(UUID userId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var entryToDelete = blacklistRepository.findBlacklistEntityByUserIdAndAndBlockedUserId(targetUserId, userId);
        var notification = new NewNotificationDto();
        notification.setUserId(targetUserId);
        notification.setNotificationType("USER_UNBLOCKED");
        notification.setNotificationText("Пользователь " + entryToDelete.getBlockedFullName() + " был удалён из блэклиста");
        streamBridge.send("newNotificationEvent-out-0", notification);
        blacklistRepository.delete(entryToDelete);
        return ResponseEntity.ok().body("User unblocked");
    }

    public ResponseEntity isInBlacklist(UUID foreignUserId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var isBlocked = blacklistRepository.existsByUserIdAndBlockedUserId(targetUserId, foreignUserId);
        return ResponseEntity.ok().body(isBlocked);
    }

    public ResponseEntity<?> updateBlockedUser(UUID userId) {
        return null;
    }

    public ResponseEntity<?> searchBlockedUser(UUID userId) {
        return null;
    }
}
