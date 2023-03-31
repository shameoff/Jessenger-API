package ru.shameoff.javalab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shameoff.javalab1.dto.CreateUpdateUserDto;
import ru.shameoff.javalab1.dto.UserDto;
import ru.shameoff.javalab1.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/login")
    public String login(){
        String token = "";
        token = "REPLACE ME";

        return token;
    }

    @PostMapping("/logout")
    public void logout(){

        return;
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody CreateUpdateUserDto createUpdateUserDto){
        return userService.register(createUpdateUserDto);
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello, guy!";
    }
}
