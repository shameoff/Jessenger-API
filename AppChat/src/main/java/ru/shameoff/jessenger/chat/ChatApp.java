package ru.shameoff.jessenger.chat;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import ru.shameoff.jessenger.common.test.EnableTestMessage;

@EnableTestMessage
@ConfigurationPropertiesScan("ru.shameoff.jessenger.chat")
@SpringBootApplication
@EnableEurekaClient
public class ChatApp {

    public static void main(String[] args) {
        SpringApplication.run(ChatApp.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}