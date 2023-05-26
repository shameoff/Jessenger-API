package ru.shameoff.jessenger.common.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static ru.shameoff.jessenger.common.security.SecurityConstants.HEADER_AUTH;
import static ru.shameoff.jessenger.common.security.SecurityConstants.TOKEN_PREFIX;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var jwt = request.getHeader(HEADER_AUTH);
        if (jwt == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is empty");
            return;
        }

        // Token parsing
        try {
            var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            var data = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt.replace(TOKEN_PREFIX, ""));
            JwtUserData userData = new JwtUserData(
                    UUID.fromString(data.getBody().getSubject()),
                    (String) data.getBody().get("username"),
                    (String) data.getBody().get("fullName")
            );
            var authentication = new JwtAuthentication(userData);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            // if token isn't valid or expired
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT is incorrect or expired");
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Я хз что, но щас починим");
        }

        filterChain.doFilter(request, response);
    }
}
