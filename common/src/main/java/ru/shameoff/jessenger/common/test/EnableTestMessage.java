package ru.shameoff.jessenger.common.test;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для включения контроллера с тестовым сообщением в каком-либо микросервисе
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({TestMessageConfig.class})
public @interface EnableTestMessage {
}
