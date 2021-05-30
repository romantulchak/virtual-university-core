package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.NotificationDTO;
import com.romantulchak.virtualuniversity.model.NotificationBox;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import com.romantulchak.virtualuniversity.payload.request.NotificationRequest;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface NotificationService {
    <T extends UserAbstract> void createAll(Collection<T> users, String message, String destination);

    <T extends UserAbstract> void create(T user, String message, NotificationBox notificationBox, String destination);

    Collection<NotificationDTO> findAllNotificationForUser(Authentication authentication, int page);

    double getNotificationCounter(long... id);

    <T extends UserAbstract> void notifyUser(T user, Object data, String destination);

    <T extends UserAbstract> void notifyUsers(Collection<T> users, Object data, String destination);

    void readNotification(NotificationRequest notificationRequest);
}
