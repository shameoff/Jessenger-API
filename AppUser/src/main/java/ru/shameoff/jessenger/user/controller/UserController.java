package ru.shameoff.jessenger.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.common.sharedDto.ErrorResponse;
import ru.shameoff.jessenger.common.sharedDto.UserDto;
import ru.shameoff.jessenger.user.dto.EditUserInfoDto;
import ru.shameoff.jessenger.user.dto.LoginDto;
import ru.shameoff.jessenger.user.dto.RegisterRequestDto;
import ru.shameoff.jessenger.user.dto.RetrieveUsersRequest;
import ru.shameoff.jessenger.user.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
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
     * @param res - вспомогательный объект для парсинга ошибок, не передается по HTTP
     * @return {@link UserDto} или сообщение с ошибкой
     */
    @Operation(summary = "Возвращает UserDto авторизованного пользователя и JWT токен в Authorization Header в случае ввода корректных данных для входа")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult res) {
        return res.hasErrors() ? ResponseEntity.badRequest().body(returnErrorResponse(res)) : userService.login(loginDto);
    }

    /**
     * Метод для регистрации пользователя. Принимает поля из сущности RegisterDto
     *
     * @param res - вспомогательный объект для парсинга ошибок, не передается по HTTP
     * @return {@link UserDto} or ErrorMessage
     */
    @Operation(summary = "Регистрирует пользователя. Принимает поля из сущности RegisterDto, возвращает UserDto нового пользователя и JWT токен в Authorization Header  в случае успешной регистрации", security = @SecurityRequirement(name = ""))
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto requestDto, BindingResult res) {
        return res.hasErrors() ? ResponseEntity.badRequest().body(returnErrorResponse(res)) : userService.register(requestDto);
    }

    /**
     * Метод для изменения параметров авторизованного пользователя, возвращает {@link UserDto} с измененными параметрами
     *
     * @param res - вспомогательный объект для парсинга ошибок, не передается по HTTP
     * @return измененная информация о пользователе как поля сущности {@link UserDto}
     */
    @Operation(summary = "Изменяет параметры авторизованного пользователя, возвращает UserDto с измененными параметрами", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/profile")
    public ResponseEntity<?> editProfile(@Valid @RequestBody EditUserInfoDto editUserInfoDto, BindingResult res) {
        return res.hasErrors() ? ResponseEntity.badRequest().body(returnErrorResponse(res)) : ResponseEntity.ok(userService.updateInfo(editUserInfoDto));
    }


    /**
     * Метод для просмотра информации о пользователе с именем пользователя = username.
     * По умолчанию, если параметр не задан, возвращает информацию об авторизованном пользователе
     *
     * @param username имя пользователя, информацию о котором хочется получить
     */
    @Operation(summary = "Возвращает информацию о пользователе по его username. Если параметр не указан, возвращает информацию об авторизованном пользователе", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/profile")
    public UserDto showProfile(@RequestParam(required = false) String username) {
        return userService.retrieveUserInfo(username);
    }

    /**
     * Возвращает информацию о пользователе с ID = userId
     *
     * @param userId UUID пользователя
     * @return {@link UserDto}
     */
    @Operation(summary = "Возвращает поля сущности UserDto о пользователе userId", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{userId}/profile")
    public UserDto showProfile(@PathVariable UUID userId) {
        return userService.retrieveUserInfo(userId);
    }

    @Operation(summary = "Возвращает список пользователей по указанным параметрам с информацией о пагинации", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/list")
    public ResponseEntity<?> retrieveUsers(@Valid @RequestBody RetrieveUsersRequest dto, BindingResult res) {
        return res.hasErrors() ? ResponseEntity.badRequest().body(returnErrorResponse(res)) : userService.retrieveUsers(dto);
    }

    /**
     * Этот эндпоинт должен блокировать refresh токен в случае, если он используется, но сейчас это просто заглушка
     */
    @PostMapping("/logout")
    @Operation(summary = "Должен блокировать refresh токен в случае, если он используется, но сейчас это просто заглушка", security = @SecurityRequirement(name = "bearerAuth"))
    public void logout() {
        return;
    }

    /**
     * Вспомогательная функция, которая собирает все ошибки из некорректного ввода в одну строку и выводит их
     *
     * @param bindingResult an object where error logs located
     * @return a string with all errors combined
     */
    private ErrorResponse returnErrorResponse(BindingResult bindingResult) {
        HashMap<String, String> messages = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> {
            var field = error.getField();
            var message = error.getDefaultMessage();
            messages.put(field, message);
        });
        return ErrorResponse.builder()
                .time(LocalDateTime.now())
                .messages(messages).build();
    }
}
