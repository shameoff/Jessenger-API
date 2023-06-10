package ru.shameoff.jessenger.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Рукописная аутентификация для JWT. В себе содержит данные пользователя, которые были взяты из токена {@link JwtUserData}
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    public JwtAuthentication(JwtUserData jwtUserData){
        super(null);
        this.setDetails(jwtUserData);
        setAuthenticated(true);
    }
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return getDetails();
    }
}
