package ru.shameoff.jessenger.fileserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.shameoff.jessenger.common.CommonBeans;
import ru.shameoff.jessenger.common.security.SecurityConfig;

@EnableJpaAuditing
@ConfigurationPropertiesScan("ru.shameoff.jessenger.fileserver")
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"ru.shameoff.jessenger"})
@Import({SecurityConfig.class, CommonBeans.class})
@OpenAPIDefinition(info = @Info(title = "Fileserver API", version = "1.0", description = "Documentation for Fileserver Service") )
public class FileserverApp {
    public static void main(String[] args) {
        SpringApplication.run(FileserverApp.class, args);
    }

}
