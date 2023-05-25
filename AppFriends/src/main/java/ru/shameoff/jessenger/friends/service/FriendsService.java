package ru.shameoff.jessenger.friends.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shameoff.jessenger.common.client.UserServiceClient;
import ru.shameoff.jessenger.common.security.JwtUserData;
import ru.shameoff.jessenger.common.security.props.SecurityProps;
import ru.shameoff.jessenger.common.sharedDto.NewNotificationDto;
import ru.shameoff.jessenger.friends.dto.AddFriendDto;
import ru.shameoff.jessenger.friends.dto.RetrieveFriendsDto;
import ru.shameoff.jessenger.friends.entity.FriendEntity;
import ru.shameoff.jessenger.friends.repository.FriendsRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final FriendsRepository friendsRepository;
    private final UserServiceClient userServiceClient;
    private final SecurityProps props;
    private final StreamBridge streamBridge;

    public ResponseEntity<?> retrieveFriends(RetrieveFriendsDto dto) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var friends = friendsRepository.findAllByUserId(targetUserId);
        return ResponseEntity.ok().body(friends);
    }

    public ResponseEntity retrieveFriendProfile(UUID friendId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var entry = friendsRepository.findByUserIdAndFriendId(targetUserId, friendId);
        return ResponseEntity.ok().body(entry);
    }
    @Transactional
    public ResponseEntity addFriend(AddFriendDto addFriendDto) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var targetUser = userServiceClient.getUserById(targetUserId, props.getIntegrationsProps().getApiKey());
        var foreignUser = userServiceClient.getUserById(UUID.fromString(addFriendDto.getFriendId()), props.getIntegrationsProps().getApiKey());
        var newEntry = new FriendEntity();
        newEntry.setUserId(targetUserId);
        newEntry.setFriendId(UUID.fromString(addFriendDto.getFriendId()));
        newEntry.setFriendFullName(foreignUser.getFullName());
        friendsRepository.save(newEntry);
        var newEntryReversed = new FriendEntity();
        newEntryReversed.setUserId(UUID.fromString(addFriendDto.getFriendId()));
        newEntryReversed.setFriendId(targetUserId);
        newEntryReversed.setFriendFullName(targetUser.getFullName());
        friendsRepository.save(newEntryReversed);
        var notification = new NewNotificationDto();
        notification.setNotificationText("Пользователь " + newEntry.getFriendFullName() + " был добавлен в друзья");
        notification.setUserId(targetUserId);
        notification.setNotificationType("FRIEND_ADDED");
        streamBridge.send("newNotificationEvent-out-0", notification);
        return ResponseEntity.ok().body("Friend added");
    }
    @Transactional
    public ResponseEntity deleteFriend(UUID foreignUserId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var entryToDelete = friendsRepository.findByUserIdAndFriendId(targetUserId, foreignUserId);
        var entryToDeleteReversed = friendsRepository.findByUserIdAndFriendId(foreignUserId, targetUserId);
        var notification = new NewNotificationDto();
        notification.setNotificationText("Пользователь " + entryToDelete.getFriendFullName() + " был удалён из друзей");
        notification.setUserId(targetUserId);
        notification.setNotificationType("FRIEND_DELETED");
        streamBridge.send("newNotificationEvent-out-0", notification);
        friendsRepository.delete(entryToDelete);
        friendsRepository.delete(entryToDeleteReversed);
        return ResponseEntity.ok().body("Friend deleted");
    }

//    public ResponseEntity updateFriend(String userId) {
//        return null;
//    }
    public ResponseEntity searchFriends(UUID userId) {
        return null;
    }
}
