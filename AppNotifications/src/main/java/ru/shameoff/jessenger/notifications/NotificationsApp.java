package ru.shameoff.jessenger.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.shameoff.jessenger.common.test.EnableTestMessage;

@EnableTestMessage
@ConfigurationPropertiesScan("ru.shameoff.jessenger.notifications")
@SpringBootApplication
public class NotificationsApp {

    public static void main(String[] args) {
        SpringApplication.run(NotificationsApp.class, args);
    }

}
