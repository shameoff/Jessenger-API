package ru.shameoff.jessenger.common.security.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Часть конфигурации Security Props, относящаяся к интеграционным запросам. Не создаётся, как отдельный бин
 */
@ToString
@Setter
@Getter
public class SecurityIntegrationsProps {

    private String rootPath;
    private String apiKey;
}
