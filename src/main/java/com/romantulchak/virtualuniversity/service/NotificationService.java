package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.NotificationDTO;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import com.romantulchak.virtualuniversity.service.impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public interface NotificationService {

    Collection<NotificationDTO> findAllNotificationForUser(Authentication authentication);

    double getNotificationCounter(Authentication authentication);
}
