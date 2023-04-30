package ru.shameoff.javalab1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shameoff.javalab1.entity.UserEntity;
import ru.shameoff.javalab1.repositories.UserRepository;
import ru.shameoff.javalab1.security.CustomUserDetails;

/**
 * Этот сервис нужен Spring Security только для того, чтобы использовать в authenticationManager для проверки полей
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return new CustomUserDetails(userEntity);
    }
}
