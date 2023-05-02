package ru.shameoff.jessenger.user.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class JwtUserData {

    private final String id;
    private final String username;
    private final String fullName;
}
