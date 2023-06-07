package ru.shameoff.jessenger.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shameoff.jessenger.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("Проверяет, существует ли юзер с данным ID")
    void ifUserExistsById_test() {
        when(repository.existsById(()))
                .thenReturn(Optional.empty());

        when(repository.existsById())
    }

    @Test
    @DisplayName("Проверяет....")
    void register() {
    }

    @Test
    void login() {
    }

    @Test
    void retrieveUserInfo() {
    }

    @Test
    void testRetrieveUserInfo() {
    }

    @Test
    void retrieveUsers() {
    }

    @Test
    void updateInfo() {
    }
}