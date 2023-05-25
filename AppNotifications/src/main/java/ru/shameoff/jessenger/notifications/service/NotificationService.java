package ru.shameoff.jessenger.notifications.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.common.sharedDto.NewNotificationDto;
import ru.shameoff.jessenger.notifications.entity.NotificationEntity;
import ru.shameoff.jessenger.notifications.repository.NotificationRepository;

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


}
