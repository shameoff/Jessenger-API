package ru.shameoff.javalab1.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.shameoff.javalab1.security.props.SecurityProps;
import ru.shameoff.javalab1.service.CustomUserDetailService;

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


    @Bean
    public SecurityFilterChain filterChainJwt(HttpSecurity http) throws Exception {
        http = http
                .authorizeRequests()
                .antMatchers("/users/register", "/users/login").permitAll()
                .and()
                .addFilterBefore(
                        new JwtTokenFilter(securityProps.getJwtTokenProps().getSecret()),
                        UsernamePasswordAuthenticationFilter.class
                );
//                .requestMatcher(filterPredicate(
//                        securityProps.getJwtTokenProps().getRootPath(),
//                        securityProps.getJwtTokenProps().getPermitAll()
//                )) В теории, вот эта штука должна работать лучше и правильнее, но пока что вот так
        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http = http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/register", "/users/login").permitAll()
                .anyRequest().authenticated()
                .and();
        return http.build();
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
