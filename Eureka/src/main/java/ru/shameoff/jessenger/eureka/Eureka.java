package ru.shameoff.jessenger.eureka;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.shameoff.jessenger.common.test.EnableTestMessage;
import ru.shameoff.jessenger.eureka.security.SecurityConfig;

@EnableTestMessage
@ConfigurationPropertiesScan("ru.shameoff.jessenger.eureka")
@SpringBootApplication
@EnableEurekaServer
@Import(SecurityConfig.class)
public class Eureka {

    public static void main(String[] args) {
        SpringApplication.run(Eureka.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
