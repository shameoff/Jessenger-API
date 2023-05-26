package ru.shameoff.jessenger.friends.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.common.client.UserServiceClient;
import ru.shameoff.jessenger.common.security.JwtUserData;
import ru.shameoff.jessenger.common.security.props.SecurityProps;
import ru.shameoff.jessenger.common.sharedDto.NewNotificationDto;
import ru.shameoff.jessenger.friends.dto.BlockedDto;
import ru.shameoff.jessenger.friends.entity.BlacklistEntity;
import ru.shameoff.jessenger.friends.repository.BlacklistRepository;
import ru.shameoff.jessenger.friends.repository.BlockedProjection;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;
    private final UserServiceClient userServiceClient;
    private final SecurityProps props;
    private final StreamBridge streamBridge;
    private final ModelMapper modelMapper;

    /**
     * Возвращает список UUIDов пользователей в блоклисте у запрашиваемого пользователя
     *
     * @param userId
     * @return List of UUIDs
     */
    public List<UUID> retrieveUserBlacklist(UUID userId) {
        return blacklistRepository.findAllBlockedIdByUserId(userId).stream().map(BlockedProjection::getBlockedId).collect(Collectors.toList());
    }

    /**
     * Получение списка заблокированных пользователей авторизованного пользователя
     *
     * @return List of {@link BlockedDto}
     */
    public ResponseEntity<?> retrieveUserBlacklist() {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var blockedUsers = blacklistRepository.findAllByUserId(targetUserId)
                .stream().map(blocked -> {
                    var blockedDto = modelMapper.map(blocked, BlockedDto.class);
                    blockedDto.setBlocked_at(blocked.getCreatedAt());
                    return blockedDto;
                }).collect(Collectors.toList());
        return ResponseEntity.ok().body(blockedUsers);
    }

    /**
     * Добавляет авторизованному пользователю в черный список внешнего пользователя
     *
     * @param foreignUserId
     * @return
     */
    public ResponseEntity<?> blockUser(UUID foreignUserId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var foreignUser = userServiceClient.getUserById(foreignUserId, props.getIntegrationsProps().getApiKey());
        var newEntry = new BlacklistEntity();
        newEntry.setUserId(targetUserId);
        newEntry.setBlockedId(foreignUserId);
        newEntry.setBlockedFullName(foreignUser.getFullName());
        var notification = NewNotificationDto.builder()
                .userId(targetUserId)
                .notificationText("Пользователь " + newEntry.getBlockedFullName() + " был добавлен в чёрный список")
                .notificationType("USER_BLOCKED").build();
        var blocked = blacklistRepository.save(newEntry);
        streamBridge.send("newNotificationEvent-out-0", notification);
        return ResponseEntity.ok().body(blocked);
    }

    /**
     * Удаляет у авторизованного пользователя из черного списка внешнего пользователя
     *
     * @param foreignUserId
     * @return
     */
    public ResponseEntity<?> unblockUser(UUID foreignUserId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var entryToDelete = blacklistRepository.findBlacklistEntityByUserIdAndBlockedId(targetUserId, foreignUserId);
        var notification = NewNotificationDto.builder()
                .userId(targetUserId)
                .notificationText("Пользователь " + entryToDelete.getBlockedFullName() + " был удалён из блэклиста")
                .notificationType("USER_UNBLOCKED").build();
        streamBridge.send("newNotificationEvent-out-0", notification);
        blacklistRepository.delete(entryToDelete);
        return ResponseEntity.ok().body("User unblocked");
    }

    /**
     * Проверяет, находится ли авторизованный пользователь у внешнего пользователя в блэклисте
     *
     * @param foreignUserId
     * @return boolean
     */
    public ResponseEntity isBlocked(UUID foreignUserId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var isBlocked = blacklistRepository.existsByUserIdAndBlockedId(foreignUserId, targetUserId);
        return ResponseEntity.ok().body(isBlocked);
    }

    /**
     * Проверяет, находится ли таргет юзер у внешнего пользователя в блэклисте
     *
     * @param targetUserId
     * @param foreignUserId
     * @return boolean
     */
    public Boolean isBlocked(UUID targetUserId, UUID foreignUserId) {
        var isBlocked = blacklistRepository.existsByUserIdAndBlockedId(foreignUserId, targetUserId);
        return isBlocked;
    }

    public ResponseEntity<?> updateBlockedUser(UUID userId) {
        return null;
    }

    public ResponseEntity<?> searchBlockedUser(UUID userId) {
        return null;
    }
}
