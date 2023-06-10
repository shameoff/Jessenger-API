package ru.shameoff.jessenger.common.security;

import lombok.experimental.UtilityClass;

/**
 * Константы, которые используются в работе Spring Security данного приложения
 */
@UtilityClass
public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTH = "Authorization";
    public static final String HEADER_INTEGRATION = "API_KEY";
}
