package ru.shameoff.jessenger.friends.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shameoff.jessenger.friends.entity.BlacklistEntity;

import java.util.List;
import java.util.UUID;

public interface BlacklistRepository extends JpaRepository<BlacklistEntity, UUID> {

    Boolean existsByUserIdAndBlockedId(UUID userId, UUID blockedUserId);
    BlacklistEntity findBlacklistEntityByUserIdAndBlockedId(UUID userId, UUID blockedUserId);
    List<BlacklistEntity> findAllByUserId(UUID targetUserId);
    List<BlockedProjection> findAllBlockedIdByUserId(UUID targetUserId);
}
