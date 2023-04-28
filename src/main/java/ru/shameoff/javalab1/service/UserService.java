package ru.shameoff.javalab1.service;


import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.shameoff.javalab1.dto.LoginDto;
import ru.shameoff.javalab1.dto.RegisterDto;
import ru.shameoff.javalab1.dto.UserDto;
import ru.shameoff.javalab1.entity.UserEntity;
import ru.shameoff.javalab1.repositories.UserRepository;
import ru.shameoff.javalab1.security.CustomUserDetails;
import ru.shameoff.javalab1.security.props.SecurityJwtTokenProps;
import ru.shameoff.javalab1.security.props.SecurityProps;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Transactional
    public ResponseEntity login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));

        String token = Jwts.builder()
                        .setSubject(authentication.getName())
                                .setExpiration(new Date(System.currentTimeMillis() + ))


        SecurityContextHolder.getContext().setAuthentication(authentication);
        var user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        System.out.println(user);
        return new ResponseEntity(modelMapper.map(user,UserDto.class), HttpStatus.OK);
    }
    @Transactional
    public UserDto retrieveInfo() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//        UserEntity userEntity = user;
//        return new UserDto(
//                userEntity.getId(),
//                userEntity.getLogin(),
//                userEntity.getEmail(),
//                userEntity.getFirstMiddleLastName(),
//                userEntity.getBirthDate(),
//                userEntity.getPhoneNumber(),
//                userEntity.getCity(),
//                userEntity.getAvatarUuid()
//        );
        return new UserDto();
    }

    @Transactional
    public ResponseEntity register(RegisterDto registerDto) {
        if (userRepository.existsUserByLogin(registerDto.getLogin())){
            return new ResponseEntity<>("User with this login already exists!", HttpStatus.BAD_REQUEST);
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
        try {
        var registeredUser = userRepository.save(user);}
        catch (Exception e) {
            ;
        }
        return new ResponseEntity<>(modelMapper.map(registeredUser, UserDto.class), HttpStatus.OK);
    }
}
