package ru.shameoff.jessenger.friends.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.shameoff.jessenger.friends.dto.FriendsFilterDto;
import ru.shameoff.jessenger.friends.entity.FriendEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendSpecifications {
    public static Specification<FriendEntity> withFilter(FriendsFilterDto dto, UUID targetUserId){
        return (root, query, criteriaBuilder) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("userId"), targetUserId));

            if (dto.getFriendFullName() != null) {
                predicates.add(criteriaBuilder.like(root.get("friendFullName"), "%" + dto.getFriendFullName() + "%"));
            }

            if (dto.getAdded_at() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), dto.getAdded_at()));
            }

            var res =  criteriaBuilder.and(predicates.toArray(new javax.persistence.criteria.Predicate[0]));
            System.out.println(res);
            return res;
        };
    }
}
