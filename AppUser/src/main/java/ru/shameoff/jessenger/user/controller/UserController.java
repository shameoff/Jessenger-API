package ru.shameoff.jessenger.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.common.sharedDto.UserDto;
import ru.shameoff.jessenger.user.dto.EditUserInfoDto;
import ru.shameoff.jessenger.user.dto.LoginDto;
import ru.shameoff.jessenger.user.dto.RegisterDto;
import ru.shameoff.jessenger.user.dto.RetrieveUsersDto;
import ru.shameoff.jessenger.user.service.UserService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * В случае ввода корректных данных для входа возвращает {@link UserDto} авторизованного пользователя
     * и JWT токен в Authorization Header
     *
     * @param bindingResult - вспомогательный объект для парсинга ошибок, не передается по HTTP
     * @return {@link UserDto} или сообщение с ошибкой
     */
    @Operation(summary = "В случае ввода корректных данных для входа возвращает UserDto авторизованного пользователя и JWT токен в Authorization Header")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ResponseEntity.badRequest().body(printErrors(bindingResult))
                : userService.login(loginDto);
    }

    /**
     * Функция для изменения параметров авторизованного пользователя, возвращает {@link UserDto} с измененными параметрами
     *
     * @param bindingResult - вспомогательный объект для парсинга ошибок, не передается по HTTP
     * @return измененная информация о пользователе как поля сущности {@link UserDto}
     */
    @Operation(summary = "Функция для изменения параметров авторизованного пользователя, возвращает UserDto с измененными параметрами")
    @PutMapping("/profile")
    public ResponseEntity<?> editProfile(@Valid @RequestBody EditUserInfoDto editUserInfoDto, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ResponseEntity.badRequest().body(printErrors(bindingResult))
                : userService.updateInfo(editUserInfoDto);
    }


    /**
     * Отображает информацию о пользователе с именем пользователя = username.
     * По умолчанию, если параметр не задан, возвращает информацию об авторизованном пользователе
     * @param username имя пользователя, информацию о котором хочется получить
     * @return
     */
    @Operation(summary = "Отображает информацию о пользователе. Если параметр не указан, по умолчанию возвращает информацию об авторизованном пользователе", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/profile")
    public UserDto showProfile(@RequestParam(required = false) String username) {
        return userService.retrieveInfo(username);
    }

    /**
     * Возвращает информацию о пользователе с ID = userId
     *
     * @param userId UUID пользователя
     * @return {@link UserDto}
     */
    @Operation(summary = "Возвращает поля сущности UserDto о пользователе userId")
    @GetMapping("/{userId}/profile")
    public UserDto showProfile(@PathVariable UUID userId) {
        return userService.retrieveInfo(userId);
    }

    /**
     * Этот эндпоинт должен блокировать refresh токен в случае, если он используется, но сейчас это просто заглушка
     */
    @PostMapping("/logout")
    @Operation(summary = "Этот эндпоинт должен блокировать refresh токен в случае, если он используется, но сейчас это просто заглушка")
    public void logout() {
        return;
    }

    /**
     * Эндпоинт для регистрации пользователя. Принимает поля из сущности RegisterDto
     *
     * @return {@link UserDto} or ErrorMessage
     */
    @Operation(summary = "Эндпоинт для регистрации пользователя. Принимает поля из сущности RegisterDto")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ResponseEntity.badRequest().body(printErrors(bindingResult))
                : userService.register(registerDto);
    }

    @Operation(summary = "Возвращает список пользователя с информацией о пагинации")
    @PostMapping("/list")
    public ResponseEntity<?> retrieveUsers(@Valid @RequestBody RetrieveUsersDto dto){
        return null;
    }
    {}

    /**
     * Вспомогательная функция, которая собирает все ошибки из некорректного ввода в одну строку и выводит их
     *
     * @param bindingResult an object where error logs located
     * @return a string with all errors combined
     */
    private String printErrors(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult.getFieldErrors().forEach(error -> errorMessage.append(String.format("Field %s is required%n", error.getField())));
        return errorMessage.toString();
        // Тут бы по-хорошему возвращать не просто сообщение, а ошибку в JSON, но потом.
    }
}
