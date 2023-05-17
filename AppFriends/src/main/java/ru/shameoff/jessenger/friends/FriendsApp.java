package ru.shameoff.jessenger.friends;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import ru.shameoff.jessenger.common.test.EnableTestMessage;

@EnableTestMessage
@ConfigurationPropertiesScan("ru.shameoff.jessenger.friends")
@SpringBootApplication
@EnableEurekaClient
public class FriendsApp {

    public static void main(String[] args) {
        SpringApplication.run(FriendsApp.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
