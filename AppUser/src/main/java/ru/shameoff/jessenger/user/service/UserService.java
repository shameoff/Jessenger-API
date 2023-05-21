package ru.shameoff.jessenger.user.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shameoff.jessenger.user.dto.EditUserInfoDto;
import ru.shameoff.jessenger.user.dto.LoginDto;
import ru.shameoff.jessenger.user.dto.RegisterDto;
import ru.shameoff.jessenger.user.dto.UserDto;
import ru.shameoff.jessenger.user.entity.UserEntity;
import ru.shameoff.jessenger.user.repository.UserRepository;
import ru.shameoff.jessenger.common.security.JwtUserData;
import ru.shameoff.jessenger.common.security.props.SecurityProps;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static ru.shameoff.jessenger.common.security.SecurityConstants.HEADER_AUTH;
import static ru.shameoff.jessenger.common.security.SecurityConstants.TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SecurityProps securityProps;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private String generateToken(UserEntity user) {
        var signKey = Keys.hmacShaKeyFor(securityProps.getJwtTokenProps().getSecret().getBytes(StandardCharsets.UTF_8));
        var expiration = new Date(System.currentTimeMillis() + securityProps.getJwtTokenProps().getExpiration());

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("fullName", user.getFullName())
                .setExpiration(expiration)
                .signWith(signKey)
                .compact();
    }

    @Transactional
    public ResponseEntity<?> login(LoginDto loginDto) {
        try {
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            System.out.println("ТОКЕН НЕ ПРИ ЧЁМ\n" + authentication + " " + authentication.getName() + " " + authentication.getPrincipal() + " " + authentication.getCredentials() + " " + authentication.getAuthorities() + " " + authentication.getDetails() + " " + "\n");

            authentication = authenticationManager.authenticate(authentication);
            var user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
            var token = generateToken(user);
            return ResponseEntity.ok()
                    .header(HEADER_AUTH, TOKEN_PREFIX + token)
                    .body(modelMapper.map(user, UserDto.class));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Incorrect credentials!");
        }
    }

    @Transactional
    public UserDto retrieveInfo() {
        System.out.println("retrieveInfo\n");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserData jwtUserData = (JwtUserData) authentication.getPrincipal();
        System.out.println(jwtUserData.getUsername() + " " + jwtUserData.getFullName() + " " + jwtUserData.getId());
        UserEntity userEntity = userRepository.findByUsername(jwtUserData.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return modelMapper.map(userEntity, UserDto.class);
    }

    public ResponseEntity<?> updateInfo(EditUserInfoDto editUserInfoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserData jwtUserData = (JwtUserData) authentication.getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(jwtUserData.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        modelMapper.map(editUserInfoDto, userEntity);
        var savedUser = userRepository.save(userEntity);
        return ResponseEntity.ok().body(modelMapper.map(savedUser, UserDto.class));
    }

    @Transactional
    public ResponseEntity<?> register(RegisterDto registerDto) {
        if (userRepository.existsUserByUsername(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body("User with this login already exists!");
        }
        if (userRepository.existsUserByEmail(registerDto.getEmail())) {
            return ResponseEntity.badRequest().body("User with this email already exists!");
        }
        var user = modelMapper.map(registerDto, UserEntity.class);
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        var registeredUser = userRepository.save(user);
        var token = generateToken(registeredUser);
        return ResponseEntity.ok()
                .header(HEADER_AUTH, TOKEN_PREFIX + token)
                .body(modelMapper.map(registeredUser, UserDto.class));
    }
}
