package ru.shameoff.jessenger.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("message")
    public ResponseEntity<String> retrieveMessage() {
        return ResponseEntity.ok("Успешный вызов /test/message");
    }
}
