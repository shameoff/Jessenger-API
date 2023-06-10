package ru.shameoff.jessenger.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static ru.shameoff.jessenger.common.security.SecurityConstants.HEADER_INTEGRATION;

/**
 * Фильтр, который выполняется при всех запросах, попадающих в фильтр цепочки интеграционных запросов.
 */
@RequiredArgsConstructor
public class IntegrationFilter extends OncePerRequestFilter {

    private final String apiKey;

    /**
     * Проверяет наличие API ключа в header, имя которого указано в константе INTEGRATION HEADER.
     * Если его нет - кидает 401 ошибку
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!Objects.equals(request.getHeader(HEADER_INTEGRATION), apiKey)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(new IntegrationAuthentication());
        filterChain.doFilter(request, response);
    }
}
