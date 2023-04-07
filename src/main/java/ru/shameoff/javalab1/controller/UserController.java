package ru.shameoff.javalab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.javalab1.dto.LoginDto;
import ru.shameoff.javalab1.dto.RegisterDto;
import ru.shameoff.javalab1.dto.UserDto;
import ru.shameoff.javalab1.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }
    @GetMapping("/profile")
    public UserDto showProfile(){
        return userService.retrieveInfo();
    }

    @PostMapping("/logout")
    public void logout(){

        return;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDto registerDto){
        return userService.register(registerDto);
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello, guy!";
    }
}
