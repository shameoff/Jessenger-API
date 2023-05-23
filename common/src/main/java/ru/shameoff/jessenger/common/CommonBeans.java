package ru.shameoff.jessenger.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class CommonBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public OpenAPI30Config openApiConfig() {return new OpenAPI30Config();}
}
