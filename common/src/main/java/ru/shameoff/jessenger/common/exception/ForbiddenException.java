package ru.shameoff.jessenger.common.exception;

/**
 * Класс ошибки.
 * ConflictException используется, когда запрос пользователя как-то конфликтует с текущим состоянием
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}