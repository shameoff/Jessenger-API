package ru.shameoff.jessenger.common.security.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SecurityJwtTokenProps {
    private String[] permitAll;

    private String secret;

    private Long expiration;

    private String rootPath;
}
