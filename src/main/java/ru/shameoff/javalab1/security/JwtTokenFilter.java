package ru.shameoff.javalab1.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static ru.shameoff.javalab1.security.SecurityConstants.HEADER_AUTH;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwt = request.getHeader(HEADER_AUTH);
        if (jwt == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // Token parsing
        JwtUserData userData;
        try {
            var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            var data = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
            userData = new JwtUserData(
                    data.getBody().getId(),
                    (String) data.getBody().get("login"),
                    (String) data.getBody().get("name")
            );
        } catch (JwtException e) {
            // if token isn't valid or expired
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        var authentication = new JwtAuthentication(userData);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
