package ru.shameoff.jessenger.common.sharedDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@Builder
public class ErrorResponse {
    private HashMap<String, String> messages;
    private LocalDateTime time;
}
