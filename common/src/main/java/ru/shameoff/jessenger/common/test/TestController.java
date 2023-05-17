package ru.shameoff.jessenger.common.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${app.test-message.current-service}")
    String currentService;
    @GetMapping("/message")
    public ResponseEntity<String> retrieveMessage() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Content-Encoding", "UTF-8");
        return new ResponseEntity<>("Тестовый ответ от " + currentService, headers, HttpStatus.OK);
    }
}
