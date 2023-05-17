package ru.shameoff.jessenger.swagger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.shameoff.jessenger.swagger.SwaggerClient;

import java.util.List;

@RequiredArgsConstructor
public class AggregatorController {

    private final List<SwaggerClient> swaggerClients;


}
