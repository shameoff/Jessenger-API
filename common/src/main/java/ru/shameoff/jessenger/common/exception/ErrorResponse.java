package ru.shameoff.jessenger.common.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Кастомная ошибка для вывода невалидных данных
 */
@Data
public class ErrorResponse {
    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    private String message;

    private LocalDateTime timestamp;

}