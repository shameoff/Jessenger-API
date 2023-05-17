package ru.shameoff.jessenger.swagger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-name")
public interface SwaggerClient {
    @RequestMapping(method = RequestMethod.GET, value = "/v3/api-docs", consumes = "application/json")
    String getSwaggerJson();
}