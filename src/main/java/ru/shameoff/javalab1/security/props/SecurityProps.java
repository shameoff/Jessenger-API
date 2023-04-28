package ru.shameoff.javalab1.security.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ToString
@Getter
@Setter
@ConfigurationPropertiesScan("app.security")
public class SecurityProps {
    private SecurityJwtTokenProps jwtTokenProps;

    private SecurityIntegrationsProps integrationsProps;
}
