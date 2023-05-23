package ru.shameoff.jessenger.common.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class JwtUserData {

    private final UUID id;
    private final String username;
    private final String fullName;
}
