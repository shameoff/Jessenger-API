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
import ru.shameoff.jessenger.user.repositories.UserRepository;
import ru.shameoff.jessenger.user.security.JwtUserData;
import ru.shameoff.jessenger.user.security.UserDetailsImpl;
import ru.shameoff.jessenger.user.security.props.SecurityProps;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static ru.shameoff.jessenger.user.security.SecurityConstants.HEADER_AUTH;
import static ru.shameoff.jessenger.user.security.SecurityConstants.TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SecurityProps securityProps;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private String generateToken(UserEntity user){
        var signKey = Keys.hmacShaKeyFor(securityProps.getJwtTokenProps().getSecret().getBytes(StandardCharsets.UTF_8));
        var expiration = new Date(System.currentTimeMillis() + securityProps.getJwtTokenProps().getExpiration());

        return Jwts.builder()
                .setSubject(user.getId())
                .claim("username", user.getUsername())
                .claim("fullName", user.getFullName())
                .setExpiration(expiration)
                .signWith(signKey)
                .compact();
    }
    @Transactional
    public ResponseEntity<?> login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Я положил объект пользователя в principal, чтобы можно было не обращаясь к БД второй раз достать его
            var user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
            return ResponseEntity.ok()
                    .header(HEADER_AUTH, TOKEN_PREFIX + generateToken(user))
                    .body(modelMapper.map(user, UserDto.class));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Incorrect credentials!");
        }
    }

    @Transactional
    public UserDto retrieveInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserData jwt = (JwtUserData) authentication.getPrincipal();
        System.out.println(jwt.getUsername() + " " + jwt.getFullName() + " " + jwt.getId());
        UserEntity userEntity = userRepository.findByUsername(((JwtUserData) authentication.getPrincipal()).getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return modelMapper.map(userEntity, UserDto.class);
    }

    public ResponseEntity<?> updateInfo(EditUserInfoDto editUserInfoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByUsername(((JwtUserData) authentication.getPrincipal()).getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        Optional.ofNullable(editUserInfoDto.getFullName()).ifPresent(userEntity::setFullName);
        Optional.ofNullable(editUserInfoDto.getBirthDate()).ifPresent(userEntity::setBirthDate);
        Optional.ofNullable(editUserInfoDto.getPhoneNumber()).ifPresent(userEntity::setPhoneNumber);
        Optional.ofNullable(editUserInfoDto.getCity()).ifPresent(userEntity::setCity);
        Optional.ofNullable(editUserInfoDto.getAvatarUuid()).ifPresent(userEntity::setAvatarUuid);
        var savedUser = userRepository.save(userEntity);
        return ResponseEntity.ok().body(modelMapper.map(savedUser, UserDto.class));
    }
    @Transactional
    public ResponseEntity<?> register(RegisterDto registerDto) {
        if (userRepository.existsUserByUsername(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body("User with this login already exists!");
        }
        if (userRepository.existsUserByEmail(registerDto.getEmail())){
            return ResponseEntity.badRequest().body("User with this email already exists!");
        }
        var user = new UserEntity(
                UUID.randomUUID().toString(),
                registerDto.getUsername(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                registerDto.getFullName(),
                registerDto.getBirthDate(),
                registerDto.getPhoneNumber(),
                registerDto.getCity(),
                registerDto.getAvatarUuid()
        );
        var registeredUser = userRepository.save(user);
        var token = generateToken(registeredUser);
        return ResponseEntity.ok()
                .header(HEADER_AUTH, TOKEN_PREFIX + token)
                .body(modelMapper.map(registeredUser, UserDto.class));
    }
}
