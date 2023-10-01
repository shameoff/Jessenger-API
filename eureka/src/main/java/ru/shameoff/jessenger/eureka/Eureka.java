package ru.shameoff.jessenger.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Точка входа в сервис Eureka - сервис, агрегирующий все остальные, который затем позволяет обращаться к сервисам по их имени, вместо ip
 */
@ConfigurationPropertiesScan("ru.shameoff.jessenger.eureka")
@SpringBootApplication
@EnableEurekaServer
public class Eureka {
    public static void main(String[] args) {
        SpringApplication.run(Eureka.class, args);
    }

}
