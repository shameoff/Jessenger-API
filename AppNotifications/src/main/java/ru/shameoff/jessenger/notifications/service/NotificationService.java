package ru.shameoff.jessenger.notifications.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.common.sharedDto.NewNotificationDto;
import ru.shameoff.jessenger.notifications.entity.NotificationEntity;
import ru.shameoff.jessenger.notifications.repository.NotificationRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    public void saveNotification(NewNotificationDto newNotificationDto) {
        var notification = modelMapper.map(newNotificationDto, NotificationEntity.class);
        notification.setStatus("NEW");
        notificationRepository.save(notification);
        System.out.println("Уведомление type = " + newNotificationDto.getNotificationType() + " userID = "
                + newNotificationDto.getUserId());
    }

    public Integer retrieveUnreadMessages() {
        return notificationRepository.countAllByStatus("NEW");
    }

    public Integer markNotifications(List<UUID> ids, String status) {
        notificationRepository.updateNotificationStatusByIds(ids, status);
        return notificationRepository.countAllByStatus("NEW");
    }
}
