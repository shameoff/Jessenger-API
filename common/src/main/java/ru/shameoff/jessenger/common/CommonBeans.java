package ru.shameoff.jessenger.common;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

/**
 * Конфигурация бинов, которые используются во всех модулях, но не относятся к Spring Security.
 * Сейчас здесь находятся конфигурации бинов OpenAPi и ModelMapper
 */
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
