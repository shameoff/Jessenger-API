package ru.shameoff.jessenger.fileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.shameoff.jessenger.common.CommonBeans;
import ru.shameoff.jessenger.common.security.SecurityConfig;

@ConfigurationPropertiesScan("ru.shameoff.jessenger.fileserver")
@EnableJpaAuditing
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"ru.shameoff.jessenger"})
@Import({SecurityConfig.class, CommonBeans.class})
public class MinioApp {
    public static void main(String[] args) {
        SpringApplication.run(MinioApp.class, args);
    }

}
