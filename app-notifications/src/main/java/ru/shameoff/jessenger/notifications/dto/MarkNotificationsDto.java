package ru.shameoff.jessenger.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MarkNotificationsDto {
    private List<UUID> ids;
    private String status;
}
