package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.NotificationDTO;
import com.romantulchak.virtualuniversity.exception.UserIsNullException;
import com.romantulchak.virtualuniversity.model.NotificationBox;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import com.romantulchak.virtualuniversity.repository.NotificationRepository;
import com.romantulchak.virtualuniversity.service.NotificationService;
import com.romantulchak.virtualuniversity.utils.NotificationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private NotificationUtility<UserAbstract> notificationUtility;
    private SessionRegistry sessionRegistry;
    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   SimpMessagingTemplate simpMessagingTemplate,
                                   SessionRegistry sessionRegistry){
        this.notificationRepository = notificationRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationUtility = new NotificationUtility<>(notificationRepository, simpMessagingTemplate);
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public <T> Collection<NotificationDTO> createAll(List<T> users, String message) {
        return notificationUtility.createAll((List<UserAbstract>) users, message);
    }

    @Override
    public NotificationDTO create(String message, NotificationBox notificationBox) {
        return notificationUtility.create(message, notificationBox);
    }

    @Override
    public Collection<NotificationDTO> findAllNotificationForUser(Authentication authentication) {
        if(authentication.getPrincipal() != null) {
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            return notificationRepository.findALlNotificationForUser(user.getNotificationBox().getId())
                    .stream()
                    .map(NotificationDTO::new)
                    .collect(Collectors.toList());
        }
        throw new UserIsNullException();
    }

    @Override
    public double getNotificationCounter(long... id) {
        if(id == null || id.length == 0) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() != null) {
                id = new long[1];
                UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
                id[0] = user.getNotificationBox().getId();
            }
        }
        if(id != null && id.length == 1) {
            return notificationRepository.countNotReadNotification(id[0]);
        }
        throw new UserIsNullException();
    }

    @Override
    public <T extends UserAbstract> void notifyUser(T user, Object data, String destination) {
            notificationUtility.send(user.getLogin(), destination, data);
    }

    @Override
    public <T extends UserAbstract> void notifyUsers(List<T> users, Object data, String destination) {
        for (T user : users) {
            notificationUtility.send(user.getLogin(), destination, getNotificationCounter(user.getNotificationBox().getId()));
        }
    }

}
