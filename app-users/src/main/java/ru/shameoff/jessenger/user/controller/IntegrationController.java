package ru.shameoff.jessenger.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shameoff.jessenger.common.sharedDto.UserDto;
import ru.shameoff.jessenger.user.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/integration/users")
@RequiredArgsConstructor
public class IntegrationController {

    private final UserService userService;

    @GetMapping("/check")
    @Operation(summary = "Проверяет наличие пользователя по его ID. Работает только как интеграционный запрос", hidden = true)
    public Boolean check(@RequestParam(required = true) UUID userId) {
        return userService.ifUserExistsById(userId);
    }

    @GetMapping("/profile")
    @Operation(summary = "Возвращает профиль пользователя по его ID. Работает только как интеграционный запрос", hidden = true)
    public UserDto showProfile(@RequestParam(required = true) UUID userId) {
        return userService.retrieveUserInfo(userId);
    }
}
