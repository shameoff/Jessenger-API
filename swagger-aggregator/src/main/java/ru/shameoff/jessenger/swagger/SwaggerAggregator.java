package ru.shameoff.jessenger.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@ConfigurationPropertiesScan("ru.shameoff.jessenger.swagger")
@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Swagger Aggregator API", version = "1.0", description = "Documentation for Swagger Aggregator") )
public class SwaggerAggregator {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerAggregator.class, args);
    }


}
