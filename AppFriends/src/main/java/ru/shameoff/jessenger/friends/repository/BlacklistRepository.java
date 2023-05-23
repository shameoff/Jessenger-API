package ru.shameoff.jessenger.friends.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shameoff.jessenger.friends.entity.BlacklistEntity;
import ru.shameoff.jessenger.friends.entity.FriendEntity;

import java.util.List;
import java.util.UUID;

public interface BlacklistRepository extends JpaRepository<BlacklistEntity, UUID> {

    public Boolean existsByUserIdAndBlockedUserId(UUID userId, UUID blockedUserId);
    public BlacklistEntity findBlacklistEntityByUserIdAndAndBlockedUserId(UUID userId, UUID blockedUserId);

    public List<BlacklistEntity> findAllByUserId(UUID targetUserId);
}
