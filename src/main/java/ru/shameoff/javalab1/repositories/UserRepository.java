package ru.shameoff.javalab1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shameoff.javalab1.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByLogin(String login);
    Boolean existsUserByLogin(String login);
    Boolean existsUserByEmail(String email);
}