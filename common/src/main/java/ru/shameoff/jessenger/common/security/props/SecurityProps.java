package ru.shameoff.jessenger.common.security.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Конфигурация, jwt и интеграционных запросов, которая парсится из application.yml
 */
@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "app.security")
@Component
public class SecurityProps {
    private SecurityJwtTokenProps jwtTokenProps;
    private SecurityIntegrationsProps integrationsProps;
}
