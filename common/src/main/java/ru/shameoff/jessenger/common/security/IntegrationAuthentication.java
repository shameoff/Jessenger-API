package ru.shameoff.jessenger.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class IntegrationAuthentication extends AbstractAuthenticationToken {


    /**
     * Создаёт токен с пустым списком прав доступа.
     * В дальнейшем, можно добавить сюда права доступа для каждого сервиса, но сейчас это не первоочередная задача.
     */
    public IntegrationAuthentication() {
        super(null);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
