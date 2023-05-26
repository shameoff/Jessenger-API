package ru.shameoff.jessenger.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class IntegrationAuthentication extends AbstractAuthenticationToken {


    /**
     * Creates a token with empty authorities.
     *
     * In future realizations it may be changed to add authorities for certain service, but now we just say,
     * that every service can do everything with others, and we control it only with SecurityConfig
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