package ru.shameoff.javalab1.security;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ru.shameoff.javalab1.security.props.SecurityProps;
import ru.shameoff.javalab1.service.CustomUserDetailService;

import java.util.Arrays;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final SecurityProps securityProps;
    private CustomUserDetailService userDetailService;

    @Autowired
    public SecurityConfig(SecurityProps securityProps, CustomUserDetailService userDetailService) {
        this.securityProps = securityProps;
        this.userDetailService = userDetailService;
    }

    /**
     * <p>Цепочка для фильтрации запросов с JWT </p>
     * <p></p>
     * Она ловит все запросы, которые начинаются на jwt-rootPath (указан в конфиге)
     * и применяет к ним фильтр для запросов с JWT,
     * исключая запросы, которые мы перечислили в конфиге jwtToken-permitAll
     *
     */
    @Bean
    public SecurityFilterChain filterChainJwt(HttpSecurity http) throws Exception {
        http = http
                .requestMatcher(filterRequestByPredicatePath(
                        securityProps.getJwtTokenProps().getRootPath(),
                        securityProps.getJwtTokenProps().getPermitAll()))
                .addFilterBefore(
                        new JwtTokenFilter(securityProps.getJwtTokenProps().getSecret()),
                        UsernamePasswordAuthenticationFilter.class
                )
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        return http.build();
    }

    /**
     * <p>Цепочка для фильтрации интеграционных запросов </p>
     *
     * Она ловит все запросы, которые начинаются на integration-rootPath (указан в конфиге)
     * и применяет к ним фильтр интеграций
     *
     */
    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChainIntegration(HttpSecurity http) {
        http = http
                .requestMatcher(filterRequestByPredicatePath(securityProps.getIntegrationsProps().getRootPath()))
                .addFilterBefore(
                        new IntegrationFilter(securityProps.getIntegrationsProps().getApiKey()),
                        UsernamePasswordAuthenticationFilter.class
                )
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        return http.build();
    }

    /**
     * Метод для проверки пути сервлета
     *
     * @param rootPath паттерн для заданного пути
     * @param ignorePath   паттерн(ы) для игнорируемых путей
     * @return {@link RequestMatcher}
     */
    private RequestMatcher filterRequestByPredicatePath(String rootPath, String... ignorePath) {
        return req -> Objects.nonNull(req.getServletPath())
                && req.getServletPath().startsWith(rootPath)
                && Arrays.stream(ignorePath).noneMatch(item -> req.getServletPath().startsWith(item));
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
