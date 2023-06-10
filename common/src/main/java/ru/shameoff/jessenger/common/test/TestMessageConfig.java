package ru.shameoff.jessenger.common.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * В рамках этого конфига в контекст подтягиваются все бины
 * текущего пакета и его суб-пакетов. Можно так же сделать
 * package scan через {@link ComponentScan} для иных пакетов
 */
@Configuration
@ComponentScan
public class TestMessageConfig {
}
