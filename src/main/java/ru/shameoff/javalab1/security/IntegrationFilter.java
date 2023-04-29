package ru.shameoff.javalab1.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.shameoff.javalab1.security.props.SecurityProps;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static ru.shameoff.javalab1.security.SecurityConstants.HEADER_INTEGRATION;

@RequiredArgsConstructor
public class IntegrationFilter extends OncePerRequestFilter {

    private final String apiKey;

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
