package ru.shameoff.jessenger.user.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.shameoff.jessenger.user.dto.UserFilterDto;
import ru.shameoff.jessenger.user.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserSpecifications {
    public static Specification<UserEntity> withFilter(UserFilterDto filter){
        LocalDate currentDate = LocalDate.now();

        return (root, query, criteriaBuilder) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (filter.getFullName() != null) {
                predicates.add(criteriaBuilder.like(root.get("fullName"), "%" + filter.getFullName() + "%"));
            }

            if (filter.getUsername() != null) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + filter.getUsername() + "%"));
            }

            if (filter.getCity() != null) {
                predicates.add(criteriaBuilder.like(root.get("city"), "%" + filter.getCity() + "%"));
            }
            /*
                Больший возраст - меньшая дата. Главное не перепутать.
             */
            if (filter.getLowerAge() != null) {
                LocalDate upperDate = currentDate.minusYears(filter.getLowerAge());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), upperDate));
            }

            if (filter.getUpperAge() != null) {
                LocalDate lowerDate = currentDate.minusYears(filter.getUpperAge()+1);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), lowerDate));
            }
            return criteriaBuilder.and(predicates.toArray(new javax.persistence.criteria.Predicate[0]));
        };
    }
}