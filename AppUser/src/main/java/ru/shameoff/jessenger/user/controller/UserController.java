package ru.shameoff.jessenger.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.user.dto.EditUserInfoDto;
import ru.shameoff.jessenger.user.dto.LoginDto;
import ru.shameoff.jessenger.user.dto.RegisterDto;
import ru.shameoff.jessenger.user.dto.UserDto;
import ru.shameoff.jessenger.user.service.UserService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * @param loginDto - JSON object which is parsed automatically
     * @param bindingResult an object to check parsing errors
     * @return {@link UserDto} or ErrorMessage
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ResponseEntity.badRequest().body(printErrors(bindingResult))
                :userService.login(loginDto);
    }

    /**
     * @param editUserInfoDto - JSON object which is parsed automatically
     * @param bindingResult - an object to check parsing errors
     * @return new fields of user in DTO or ErrorMessage
     */
    @PutMapping("/profile")
    public ResponseEntity<?> editProfile(@Valid @RequestBody EditUserInfoDto editUserInfoDto, BindingResult bindingResult){
        return bindingResult.hasErrors() ? ResponseEntity.badRequest().body(printErrors(bindingResult))
                :userService.updateInfo(editUserInfoDto);
    }

    @GetMapping("/{userId}")
    public UserDto showProfile(@PathVariable UUID userId){
        return userService.retrieveInfo(userId);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/profile")
    public UserDto showProfile(){
         return userService.retrieveInfo();
    }

    /**
     * This endpoint usually used to block refresh token, but I don't have it, so...
     */
    @PostMapping("/logout")
    public void logout(){
        return;
    }

    /**
     * @param registerDto should have
     * @return {@link UserDto} or ErrorMessage
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult){
        return bindingResult.hasErrors() ? ResponseEntity.badRequest().body(printErrors(bindingResult))
                : userService.register(registerDto);
    }

    /**
     * Function that combines all errors from invalid input in one string
     * @param bindingResult an object where error logs located
     * @return a string with all errors combined
     */
    private String printErrors(BindingResult bindingResult){
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> errorMessage.append(String.format("Field %s is required%n", error.getField())));
            return errorMessage.toString();
            // Тут бы по-хорошему возвращать не просто сообщение, а ошибку в JSON, но потом.
    }
}
