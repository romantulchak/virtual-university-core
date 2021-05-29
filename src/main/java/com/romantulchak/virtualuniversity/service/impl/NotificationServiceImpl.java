package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.NotificationDTO;
import com.romantulchak.virtualuniversity.exception.NotificationNotFound;
import com.romantulchak.virtualuniversity.exception.UserIsNullException;
import com.romantulchak.virtualuniversity.model.NotificationBox;
import com.romantulchak.virtualuniversity.model.Resource;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import com.romantulchak.virtualuniversity.payload.request.NotificationRequest;
import com.romantulchak.virtualuniversity.repository.NotificationBoxRepository;
import com.romantulchak.virtualuniversity.repository.NotificationRepository;
import com.romantulchak.virtualuniversity.service.NotificationService;
import com.romantulchak.virtualuniversity.utils.NotificationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private NotificationUtility<UserAbstract> notificationUtility;
    private SessionRegistry sessionRegistry;
    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   SessionRegistry sessionRegistry,
                                   SimpMessagingTemplate simpMessagingTemplate){
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
    public Collection<NotificationDTO> findAllNotificationForUser(Authentication authentication, int page) {
        if(authentication.getPrincipal() != null) {
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
        if(id == null || id.length == 0) {
            Authentication authentication = getAuthentication();
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
            notificationUtility.send(user.getLogin(), Resource.NOTIFICATION_COUNTER_DESTINATION, getNotificationCounter(user.getNotificationBox().getId()));
    }

    @Override
    public <T extends UserAbstract> void notifyUsers(List<T> users, Object data, String destination) {
        for (T user : users) {
            notificationUtility.send(user.getLogin(), destination, getNotificationCounter(user.getNotificationBox().getId()));
        }
    }

    @Transactional
    @Override
    public void readNotification(NotificationRequest notificationRequest) {
        if (notificationRequest != null) {
            if (notificationRepository.existsByIdAndReadIsFalse(notificationRequest.getNotificationId())) {
                notificationRepository.readNotification(notificationRequest.getNotificationId());
                notificationUtility.send(notificationRequest.getUsername(), Resource.NOTIFICATION_READ, "true");
            } else {
                throw new NotificationNotFound();
            }
        }
    }

    private Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
