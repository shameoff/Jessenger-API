package ru.shameoff.jessenger.swagger.controller;

import org.springdoc.core.AbstractSwaggerUiConfigProperties;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class SwaggerController {

    private final EurekaDiscoveryClient discoveryClient;

    public SwaggerController(EurekaDiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/swagger-config.json")
    public Map<String, Object> swaggerConfig() {
        List<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new LinkedList<>();
        discoveryClient.getServices().forEach(serviceName ->
                discoveryClient.getInstances(serviceName).forEach(serviceInstance -> {
                            var swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
                            swaggerUrl.setUrl(serviceInstance.getUri() + "/v3/api-docs");
                            swaggerUrl.setName(serviceName);
                            swaggerUrl.setDisplayName(serviceName);
                            urls.add(swaggerUrl);
                        }
                )
        );
        return Map.of("urls", urls);
    }
}
