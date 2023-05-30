package ru.shameoff.jessenger.user.service;


import feign.FeignException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.shameoff.jessenger.common.client.FriendsServiceClient;
import ru.shameoff.jessenger.common.security.JwtUserData;
import ru.shameoff.jessenger.common.security.props.SecurityProps;
import ru.shameoff.jessenger.common.sharedDto.*;
import ru.shameoff.jessenger.user.dto.*;
import ru.shameoff.jessenger.user.entity.UserEntity;
import ru.shameoff.jessenger.user.repository.UserRepository;
import ru.shameoff.jessenger.user.repository.UserSpecifications;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static ru.shameoff.jessenger.common.security.SecurityConstants.HEADER_AUTH;
import static ru.shameoff.jessenger.common.security.SecurityConstants.TOKEN_PREFIX;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final SecurityProps securityProps;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final StreamBridge streamBridge;
    private final FriendsServiceClient friendsServiceClient;

    public Boolean ifUserExistsById(UUID userId) {
        return userRepository.existsById(userId);
    }

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
    public ResponseEntity<?> register(RegisterRequestDto requestDto) {
        if (userRepository.existsUserByUsername(requestDto.getUsername())) {
            return ResponseEntity.badRequest().body("User with this login already exists!");
        }
        if (userRepository.existsUserByEmail(requestDto.getEmail())) {
            return ResponseEntity.badRequest().body("User with this email already exists!");
        }
        var user = modelMapper.map(requestDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        var registeredUser = userRepository.save(user);
        var token = generateToken(registeredUser);
        return ResponseEntity.ok()
                .header(HEADER_AUTH, TOKEN_PREFIX + token)
                .body(modelMapper.map(registeredUser, UserDto.class));
    }

    @Transactional
    public ResponseEntity<?> login(LoginDto loginDto) {
        try {
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            authentication = authenticationManager.authenticate(authentication);
            var user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
            var token = generateToken(user);
            var notification = NewNotificationDto.builder()
                    .userId(user.getId())
                    .notificationText("Успешный вход в систему в " + LocalDateTime.now())
                    .notificationType("SUCCESSFUL_LOGIN").build();
            streamBridge.send("newNotificationEvent-out-0", notification);
            return ResponseEntity.ok()
                    .header(HEADER_AUTH, TOKEN_PREFIX + token)
                    .body(modelMapper.map(user, UserDto.class));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Incorrect credentials!");
        }
    }

    /**
     * Получение информации о пользователе по userID. Используется в интеграционных запросах
     * @param userId
     * @return
     */
    @Transactional
    public UserDto retrieveUserInfo(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return modelMapper.map(userEntity, UserDto.class);
    }

    /**
     * Получение информации о пользователе по username
     * @param username
     * @return
     */
    @Transactional
    public ResponseEntity<?> retrieveUserInfo(String username) {
        var jwtUserData = (JwtUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (username == null) {
            username = jwtUserData.getUsername();
            UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
            return ResponseEntity.ok().body(modelMapper.map(userEntity, UserDto.class));
        }
        var targetUserId = jwtUserData.getId();
        UserEntity foreignUserEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        var isBlocked = friendsServiceClient.checkIfBlocked(targetUserId, foreignUserEntity.getId(), securityProps.getIntegrationsProps().getApiKey());
        if (!isBlocked){
            return ResponseEntity.ok().body(modelMapper.map(foreignUserEntity, UserDto.class));
        }
        return ResponseEntity.status(403).body("Пользователь ограничил доступ к своему профилю");
    }

    /** Метод, возвращающий список пользователей
     *
     * @param dto параметры пагинации, сортировки и фильтрации
     * @return список пользователей по заданным критериям и порядку
     */
    @Transactional
    public ResponseEntity<?> retrieveUsers(RetrieveUsersRequest dto) {
        var sorting = dto.getSortingDto() != null ? dto.getSortingDto().createSortingList() : new ArrayList<Sort.Order>();
        var pagination = dto.getPaginationDto() != null ? dto.getPaginationDto() : new PaginationDto(0, 10);
        var spec = dto.getFilterDto() != null ? UserSpecifications.withFilter(dto.getFilterDto()) : null;
        Pageable pageable = PageRequest.of(
                pagination.getPage(),
                pagination.getPageSize(),
                Sort.by(sorting));
        var page = userRepository.findAll(spec, pageable).map(userEntity -> modelMapper.map(userEntity, UserDto.class));
        return ResponseEntity.ok().body(page);
    }

    /**
     * Метод, обновляющий информацию о пользователе. Если поле не указано, оно остается прежним
     *
     * @param editUserInfoDto
     * @return
     */
    public UserDto updateInfo(EditUserInfoDto editUserInfoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserData jwtUserData = (JwtUserData) authentication.getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(jwtUserData.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        modelMapper.map(editUserInfoDto, userEntity);
        var savedUser = userRepository.save(userEntity);
        streamBridge.send("profileUpdateEvent-out-1", modelMapper.map(savedUser, ProfileUpdateEventDto.class));
        return modelMapper.map(savedUser, UserDto.class);
    }



}
