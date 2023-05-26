package ru.shameoff.jessenger.notifications.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.notifications.dto.MarkNotificationsDto;
import ru.shameoff.jessenger.notifications.service.NotificationService;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor

public class NotificationController {

    private final NotificationService notificationService;
    @GetMapping("")
    public Integer getUnreadMessages() {
        return notificationService.retrieveUnreadMessages();
    }

    @PostMapping("/mark")
    public Integer markMessages(@RequestBody MarkNotificationsDto markNotificationsDto) {
        return notificationService.markNotifications(markNotificationsDto.getIds(), markNotificationsDto.getStatus());
    }

}
