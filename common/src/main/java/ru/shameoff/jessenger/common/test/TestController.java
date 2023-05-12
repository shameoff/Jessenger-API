package ru.shameoff.jessenger.common.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common/test")
public class TestController {

    @Value("${app.test-message.current-service}")
    String currentService;
    @GetMapping("/message")
    public ResponseEntity<String> retrieveMessage() {
        return ResponseEntity.ok("Тестовый ответ от" + currentService);
    }
}
