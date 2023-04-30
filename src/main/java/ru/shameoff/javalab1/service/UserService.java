package ru.shameoff.javalab1.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shameoff.javalab1.dto.LoginDto;
import ru.shameoff.javalab1.dto.RegisterDto;
import ru.shameoff.javalab1.dto.UserDto;
import ru.shameoff.javalab1.entity.UserEntity;
import ru.shameoff.javalab1.repositories.UserRepository;
import ru.shameoff.javalab1.security.props.SecurityProps;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import static ru.shameoff.javalab1.security.SecurityConstants.HEADER_AUTH;
import static ru.shameoff.javalab1.security.SecurityConstants.TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SecurityProps securityProps;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private String generateToken(Authentication authentication){
        var signKey = Keys.hmacShaKeyFor(securityProps.getJwtTokenProps().getSecret().getBytes(StandardCharsets.UTF_8));
        var expiration = new Date(System.currentTimeMillis() + securityProps.getJwtTokenProps().getExpiration());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(expiration)
                .signWith(signKey)
                .compact();
    }
    @Transactional
    public ResponseEntity<?> login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            var user = (UserEntity) authentication.getPrincipal();
            return ResponseEntity.ok()
                    .header(HEADER_AUTH, TOKEN_PREFIX + generateToken(authentication))
                    .body(modelMapper.map(user, UserDto.class));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("");
        }
    }

    @Transactional
    public UserDto retrieveInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        return new UserDto(
                userEntity.getId(),
                userEntity.getLogin(),
                userEntity.getEmail(),
                userEntity.getFirstMiddleLastName(),
                userEntity.getBirthDate(),
                userEntity.getPhoneNumber(),
                userEntity.getCity(),
                userEntity.getAvatarUuid()
        );
    }

    public ResponseEntity<?> updateInfo(UserDto userDto) {
        if (userRepository.existsUserByLogin(userDto.getLogin())) {
            return ResponseEntity.badRequest().body("User with this login already exists!");
        }
        return ResponseEntity.ok("Temp");
    }
    @Transactional
    public ResponseEntity<?> register(RegisterDto registerDto) {
        if (userRepository.existsUserByLogin(registerDto.getLogin())) {
            return ResponseEntity.badRequest().body("User with this login already exists!");
        }
        if (userRepository.existsUserByEmail(registerDto.getEmail())){
            return ResponseEntity.badRequest().body("User with this email already exists!");
        }
        var user = new UserEntity(
                UUID.randomUUID().toString(),
                registerDto.getLogin(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                registerDto.getFirstMiddleLastName(),
                registerDto.getBirthDate(),
                registerDto.getPhoneNumber(),
                registerDto.getCity(),
                registerDto.getAvatarUuid()
        );
        var registeredUser = userRepository.save(user);
        return ResponseEntity.ok().body(modelMapper.map(registeredUser, UserDto.class));
    }
}
