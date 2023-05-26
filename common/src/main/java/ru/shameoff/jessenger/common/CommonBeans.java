package ru.shameoff.jessenger.common;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class CommonBeans {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Настраиваем условие для пропуска null значений
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @Bean
    public OpenAPI30Config openApiConfig() {return new OpenAPI30Config();}
}
