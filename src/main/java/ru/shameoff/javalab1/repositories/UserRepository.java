package ru.shameoff.javalab1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shameoff.javalab1.entity.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, String> {
}