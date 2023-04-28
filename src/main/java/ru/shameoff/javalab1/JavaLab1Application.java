package ru.shameoff.javalab1;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import ru.shameoff.javalab1.security.props.SecurityProps;

@ConfigurationPropertiesScan("ru.shameoff.javalab1")
@SpringBootApplication
public class JavaLab1Application {

    public static void main(String[] args) {
        SpringApplication.run(JavaLab1Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    SecurityProps securityProps() {
        return new SecurityProps();
    }
}
