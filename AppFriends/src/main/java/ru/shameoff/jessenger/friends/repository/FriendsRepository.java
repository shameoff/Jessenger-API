package ru.shameoff.jessenger.friends.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.shameoff.jessenger.friends.entity.FriendEntity;

import java.util.List;
import java.util.UUID;

public interface FriendsRepository extends JpaRepository<FriendEntity, UUID> {
    List<FriendEntity> findAllByUserId(UUID targetUserId);
    Page<FriendEntity> findAll(Specification<FriendEntity> specification, Pageable pageable);

    List<FriendIdProjection> findAllFriendIdsByUserId(UUID targetUserId);
    FriendEntity findByUserIdAndFriendId(UUID targetUserId, UUID friendId);

    Boolean existsByUserIdAndFriendId(UUID userId, UUID friendId);
}
