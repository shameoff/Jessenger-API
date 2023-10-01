package ru.shameoff.jessenger.notifications.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shameoff.jessenger.common.sharedDto.NewNotificationDto;

import java.util.function.Consumer;


/*
    unblockedUserEvent-out-0:
      destination: ${app.id}_USER_EVENT_MODIFIED
    newFriendAddedEvent-out-0:
      destination: ${app.id}_USER_EVENT_MODIFIED
    deletedFriendEvent-out-0:
      destination: ${app.id}_USER_EVENT_MODIFIED
    newBlockedUserEvent-out-0:
      destination: ${app.id}_USER_EVENT_MODIFIED
    successfulLoginEvent-in-0:
      destination: ${app.id}_USER_EVENT_SUCCESSFUL_LOGIN
 */
@Configuration
@RequiredArgsConstructor
public class RabbitUserEventListener {
    private final NotificationService notificationService;

    /**
     * Функция, создающая бин Consumer для Cloud Stream
     */
    @Bean
    public Consumer<NewNotificationDto> newNotificationEvent() {
        return notificationService::saveNotification;
    }
}
