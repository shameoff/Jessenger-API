package ru.shameoff.javalab1.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class JwtUserData {

    public final String id;
    public final String login;
    public final String name;
}
