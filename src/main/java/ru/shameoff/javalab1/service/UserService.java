package ru.shameoff.javalab1.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.shameoff.javalab1.dto.CreateUpdateUserDto;
import ru.shameoff.javalab1.dto.UserDto;
import ru.shameoff.javalab1.entity.User;
import ru.shameoff.javalab1.repositories.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto register(@RequestBody CreateUpdateUserDto createUpdateUserDto) {
        var user = new User(
                UUID.randomUUID().toString(),
                createUpdateUserDto.getLogin(),
                createUpdateUserDto.getFirstName(),
                createUpdateUserDto.getMiddleName(),
                createUpdateUserDto.getLastName(),
                createUpdateUserDto.getBirthDate()
        );
        var registeredUser = userRepository.save(user);

        return new UserDto(
                registeredUser.getId(),
                registeredUser.getLogin(),
                registeredUser.getFirstName(),
                registeredUser.getMiddleName(),
                registeredUser.getLastName(),
                registeredUser.getBirthDate()
        );
    }
}
