package ru.shameoff.jessenger.user;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.shameoff.jessenger.common.CommonBeans;
import ru.shameoff.jessenger.common.OpenAPI30Config;
import ru.shameoff.jessenger.common.security.SecurityConfig;
import ru.shameoff.jessenger.common.security.props.SecurityProps;
import ru.shameoff.jessenger.common.test.EnableTestMessage;

@EnableTestMessage
@ConfigurationPropertiesScan("ru.shameoff.jessenger.user")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableEurekaClient
@EnableFeignClients
@Import({CommonBeans.class, SecurityConfig.class})
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }

}
