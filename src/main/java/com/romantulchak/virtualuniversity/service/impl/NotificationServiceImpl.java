package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.NotificationDTO;
import com.romantulchak.virtualuniversity.exception.NotificationNotFound;
import com.romantulchak.virtualuniversity.exception.UserIsNullException;
import com.romantulchak.virtualuniversity.model.Notification;
import com.romantulchak.virtualuniversity.model.NotificationBox;
import com.romantulchak.virtualuniversity.model.Resource;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import com.romantulchak.virtualuniversity.payload.request.NotificationRequest;
import com.romantulchak.virtualuniversity.repository.NotificationRepository;
import com.romantulchak.virtualuniversity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   SimpMessagingTemplate simpMessagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public <T extends UserAbstract> void createAll(Collection<T> users, String message, String destination) {
        for (T user : users) {
            if (user.getNotificationBox() != null) {
                Notification notification = new Notification(message, user.getNotificationBox());
                notificationRepository.save(notification);
            }
        }
        notifyUsers(users, message, destination);
    }

    @Override
    public <T extends UserAbstract> void create(T user, String message, NotificationBox notificationBox, String destination) {
        notificationRepository.save(new Notification(message, notificationBox));
        notifyUser(user, message, destination);
    }

    @Override
    public Collection<NotificationDTO> findAllNotificationForUser(Authentication authentication, int page) {
        if (authentication.getPrincipal() != null) {
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, 20);
            return notificationRepository.findALlNotificationForUser(user.getNotificationBox().getId(), pageable)
                    .stream()
                    .map(NotificationDTO::new)
                    .collect(Collectors.toList());
        }
        throw new UserIsNullException();
    }

    @Override
    public double getNotificationCounter(long... id) {
        if (id == null || id.length == 0) {
            Authentication authentication = getAuthentication();
            if (authentication.getPrincipal() != null) {
                id = new long[1];
                UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
                id[0] = user.getNotificationBox().getId();
            }
        }
        if (id != null && id.length == 1) {
            return notificationRepository.countNotReadNotification(id[0]);
        }
        throw new UserIsNullException();
    }

    @Override
    public <T extends UserAbstract> void notifyUser(T user, Object data, String destination) {
        send(user.getLogin(),data, destination);
        send(user.getLogin(), getNotificationCounter(user.getNotificationBox().getId()), Resource.NOTIFICATION_COUNTER_DESTINATION);
    }

    @Async
    @Override
    public <T extends UserAbstract> void notifyUsers(Collection<T> users, Object data, String destination) {
        for (T user : users) {
            if (user.getNotificationBox() != null) {
                if (data != null) {
                    send(user.getLogin(), data, destination);
                }
                send(user.getLogin(),getNotificationCounter(user.getNotificationBox().getId()), Resource.NOTIFICATION_COUNTER_DESTINATION);
            }
        }
    }

    @Override
    public void notifyChanel(Object data, String destination) {

    }

    @Async
    protected void send(String username, Object data, String destination) {
        simpMessagingTemplate.convertAndSendToUser(username, destination, data);
    }

    @Async
    protected void send(Object data, String destination){
        simpMessagingTemplate.convertAndSend(destination, data);
    }

    @Transactional
    @Override
    public void readNotification(NotificationRequest notificationRequest) {
        if (notificationRequest != null) {
            if (notificationRepository.existsByIdAndReadIsFalse(notificationRequest.getNotificationId())) {
                notificationRepository.readNotification(notificationRequest.getNotificationId());
                send(notificationRequest.getUsername(), Resource.NOTIFICATION_READ_TOPIC, "true");
            } else {
                throw new NotificationNotFound();
            }
        }
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
