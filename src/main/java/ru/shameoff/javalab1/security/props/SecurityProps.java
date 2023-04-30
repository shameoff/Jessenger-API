package ru.shameoff.javalab1.security.props;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "app.security")
@Component
public class SecurityProps {
    private String check;
    private SecurityJwtTokenProps jwtTokenProps;
    private SecurityIntegrationsProps integrationsProps;
}
