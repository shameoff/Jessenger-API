package ru.shameoff.jessenger.friends;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.shameoff.jessenger.common.CommonBeans;
import ru.shameoff.jessenger.common.client.UserServiceClient;
import ru.shameoff.jessenger.common.security.SecurityConfig;
import ru.shameoff.jessenger.common.test.EnableTestMessage;


@EnableJpaAuditing
@EnableTestMessage
@ConfigurationPropertiesScan("ru.shameoff.jessenger.friends")
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"ru.shameoff.jessenger"})
@Import({SecurityConfig.class, CommonBeans.class})
@OpenAPIDefinition(info = @Info(title = "Friends API", version = "1.0", description = "Documentation for Friends Service") )
public class FriendsApp {
    public static void main(String[] args) {
        SpringApplication.run(FriendsApp.class, args);
    }

}

