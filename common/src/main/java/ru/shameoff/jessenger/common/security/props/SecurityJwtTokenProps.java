package ru.shameoff.jessenger.common.security.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Часть конфигурации, относящаяся к запросам с JWT токеном. Не создаётся, как отдельный бин
 */
@ToString
@Getter
@Setter
public class SecurityJwtTokenProps {
    private String[] permitAll;

    private String secret;

    private Long expiration;

    private String rootPath;
}
