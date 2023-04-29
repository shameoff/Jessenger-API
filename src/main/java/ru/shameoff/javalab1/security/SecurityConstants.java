package ru.shameoff.javalab1.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {
    public static final String TOKEN_SECRET = "IHopeIWillUnderstandSpringSecurity";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTH = "Authorization";
    public static final String HEADER_INTEGRATION = "API_KEY";
}
