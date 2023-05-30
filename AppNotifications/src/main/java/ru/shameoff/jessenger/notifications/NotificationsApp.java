package ru.shameoff.jessenger.notifications;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import ru.shameoff.jessenger.common.CommonBeans;
import ru.shameoff.jessenger.common.security.SecurityConfig;
import ru.shameoff.jessenger.common.test.EnableTestMessage;

@EnableTestMessage
@ConfigurationPropertiesScan("ru.shameoff.jessenger.notifications")
@SpringBootApplication
@EnableEurekaClient
@Import({SecurityConfig.class, CommonBeans.class})
@OpenAPIDefinition(info = @Info(title = "Notifications API", version = "1.0", description = "Documentation for Notifications Service") )
public class NotificationsApp {
    public static void main(String[] args) {
        SpringApplication.run(NotificationsApp.class, args);
    }
}
