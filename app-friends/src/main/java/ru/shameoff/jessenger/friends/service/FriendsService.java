package ru.shameoff.jessenger.friends.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shameoff.jessenger.common.client.UserServiceClient;
import ru.shameoff.jessenger.common.security.JwtUserData;
import ru.shameoff.jessenger.common.security.props.SecurityProps;
import ru.shameoff.jessenger.common.sharedDto.NewNotificationDto;
import ru.shameoff.jessenger.friends.dto.AddFriendDto;
import ru.shameoff.jessenger.friends.dto.FriendDto;
import ru.shameoff.jessenger.friends.dto.RetrieveFriendsRequest;
import ru.shameoff.jessenger.friends.entity.FriendEntity;
import ru.shameoff.jessenger.friends.repository.FriendIdProjection;
import ru.shameoff.jessenger.friends.repository.FriendSpecifications;
import ru.shameoff.jessenger.friends.repository.FriendsRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final FriendsRepository friendsRepository;
    private final UserServiceClient userServiceClient;
    private final SecurityProps props;
    private final StreamBridge streamBridge;
    private final ModelMapper modelMapper;

    /**
     * Получение списка UUIDов друзей пользователя по его id
     * @param userId UUID пользователя
     * @return список UUIDов друзей
     */
    public List<UUID> retrieveUserFriendsIds(UUID userId) {
        return friendsRepository.findAllFriendIdsByUserId(userId).stream().map(FriendIdProjection::getFriendId).collect(Collectors.toList());
    }

    /**
     * Получение списка друзей авторизованного пользователя с пагинацией и фильтрацией
     * @param dto принимаемые значения для поиска(пагинация и фильтрация)
     * @return список друзей
     */
    public Page<FriendDto> retrieveUserFriends(RetrieveFriendsRequest dto) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        Pageable pageable = PageRequest.of(
                dto.getPagination().getPage(),
                dto.getPagination().getPageSize());
        var spec = dto.getFilterDto() != null ? FriendSpecifications.withFilter(dto.getFilterDto(), targetUserId) : null;
        return friendsRepository.findAll(spec, pageable)
                .map(friend -> {
                    var friendDto = modelMapper.map(friend, FriendDto.class);
                    friendDto.setAdded_at(friend.getCreatedAt());
                    return friendDto;
                });
    }

    /**
     * Возвращает о друге все поля из сущности друзей
     */
    public FriendDto retrieveFriendProfile(UUID friendId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var friendEntity = friendsRepository.findByUserIdAndFriendId(targetUserId, friendId);
        return FriendDto.builder()
                .friendId(friendEntity.getFriendId())
                .friendFullName(friendEntity.getFriendFullName())
                .added_at(friendEntity.getCreatedAt()).build();

    }

    /**
     * Добавление друга в список друзей авторизованного пользователя
     * @param dto DTO с данными пользователя, которого нужно добавить в друзья
     */
    @Transactional
    public void addFriend(AddFriendDto dto) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var targetUser = userServiceClient.getUserById(targetUserId, props.getIntegrationsProps().getApiKey());
        var foreignUser = userServiceClient.getUserById(dto.getFriendId(), props.getIntegrationsProps().getApiKey());
        var newEntry = FriendEntity.builder()
                .userId(targetUserId)
                .friendId(dto.getFriendId())
                .friendFullName(foreignUser.getFullName()).build();
        friendsRepository.save(newEntry);
        var newEntryReversed = FriendEntity.builder()
                .userId(dto.getFriendId())
                .friendId(targetUserId)
                .friendFullName(targetUser.getFullName()).build();
        friendsRepository.save(newEntryReversed);
        var notification = NewNotificationDto.builder()
                .notificationText("Пользователь " + newEntry.getFriendFullName() + " был добавлен в друзья")
                .userId(targetUserId)
                .notificationType("FRIEND_ADDED").build();
        streamBridge.send("newNotificationEvent-out-0", notification);
    }

    /**
     * Удаление пользователя из друзей по его id
     * @param foreignUserId UUID пользователя, которого нужно удалить из друзей
     */
    @Transactional
    public void deleteFriend(UUID foreignUserId) {
        var jwtData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var targetUserId = jwtData.getId();
        var entryToDelete = friendsRepository.findByUserIdAndFriendId(targetUserId, foreignUserId);
        var entryToDeleteReversed = friendsRepository.findByUserIdAndFriendId(foreignUserId, targetUserId);
        var notification = NewNotificationDto.builder()
                .notificationText("Пользователь " + entryToDelete.getFriendFullName() + " был удален из друзей")
                .userId(targetUserId)
                .notificationType("FRIEND_DELETED").build();
        streamBridge.send("newNotificationEvent-out-0", notification);
        friendsRepository.delete(entryToDelete);
        friendsRepository.delete(entryToDeleteReversed);
    }
}