package ru.shameoff.jessenger.chat;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.shameoff.jessenger.common.CommonBeans;
import ru.shameoff.jessenger.common.security.SecurityConfig;
import ru.shameoff.jessenger.common.test.EnableTestMessage;

@EnableTestMessage
@ConfigurationPropertiesScan("ru.shameoff.jessenger.chat")
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"ru.shameoff.jessenger"})
@Import({SecurityConfig.class, CommonBeans.class})
public class ChatApp {

    public static void main(String[] args) {
        SpringApplication.run(ChatApp.class, args);
    }

}

