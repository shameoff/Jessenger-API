package ru.shameoff.jessenger.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import ru.shameoff.jessenger.common.CommonBeans;
import ru.shameoff.jessenger.common.security.SecurityConfig;
import ru.shameoff.jessenger.common.test.EnableTestMessage;

@EnableTestMessage
@ConfigurationPropertiesScan("ru.shameoff.jessenger.notifications")
@SpringBootApplication
@Import({SecurityConfig.class, CommonBeans.class})
public class NotificationsApp {

    public static void main(String[] args) {
        SpringApplication.run(NotificationsApp.class, args);
    }

}
