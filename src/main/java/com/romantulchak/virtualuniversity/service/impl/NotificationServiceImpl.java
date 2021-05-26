package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.NotificationDTO;
import com.romantulchak.virtualuniversity.exception.UserIsNullException;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import com.romantulchak.virtualuniversity.model.enumes.RoleType;
import com.romantulchak.virtualuniversity.repository.NotificationRepository;
import com.romantulchak.virtualuniversity.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
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
    public double getNotificationCounter(Authentication authentication) {
        if (authentication.getPrincipal() != null){
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            return notificationRepository.countNotReadNotification(user.getNotificationBox().getId());
        }
        throw new UserIsNullException();
    }
}
