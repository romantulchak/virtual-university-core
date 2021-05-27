package com.romantulchak.virtualuniversity.utils;

import com.romantulchak.virtualuniversity.dto.NotificationDTO;
import com.romantulchak.virtualuniversity.model.Notification;
import com.romantulchak.virtualuniversity.model.NotificationBox;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import com.romantulchak.virtualuniversity.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationUtility<T extends UserAbstract> {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationUtility(NotificationRepository notificationRepository,
                               SimpMessagingTemplate simpMessagingTemplate){
        this.notificationRepository = notificationRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public NotificationDTO create(String message, NotificationBox notificationBox){
        Notification notification = notificationRepository.save(new Notification(message, notificationBox));
        return new NotificationDTO(notification);
    }

    public List<NotificationDTO> createAll(List<T> users, String message){
        List<Notification> notificationsForSave = new ArrayList<>();
        for (T user : users) {
            if(user.getNotificationBox() != null) {
                Notification notification = new Notification(message, user.getNotificationBox());
                notificationsForSave.add(notification);
            }
        }
        List<Notification> notifications = notificationRepository.saveAll(notificationsForSave);
        return notifications.stream()
                .map(NotificationDTO::new)
                .collect(Collectors.toList());
    }

    @Async
    public void send(String username, String destination, Object data){
        simpMessagingTemplate.convertAndSendToUser(username, destination, data);
    }
}
