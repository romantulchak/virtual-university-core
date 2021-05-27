package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.NotificationDTO;
import com.romantulchak.virtualuniversity.model.NotificationBox;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.List;

public interface NotificationService {
    <T> Collection<NotificationDTO> createAll(List<T> users, String message);

    NotificationDTO create(String message, NotificationBox notificationBox);

    Collection<NotificationDTO> findAllNotificationForUser(Authentication authentication);

    double getNotificationCounter(long... id);

    <T extends UserAbstract> void notifyUser(T user, Object data, String destination);

    <T extends UserAbstract> void notifyUsers(List<T> users, Object data, String destination);
}
