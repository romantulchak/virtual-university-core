package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.NotificationDTO;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.payload.request.NotificationRequest;
import com.romantulchak.virtualuniversity.service.impl.NotificationServiceImpl;
import com.romantulchak.virtualuniversity.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(value = "*", maxAge = 3600L)
public class NotificationController {
    private final NotificationServiceImpl notificationService;

    @Autowired
    public NotificationController(NotificationServiceImpl notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping("/{page}")
    @PreAuthorize("not isAnonymous()")
    @JsonView(Views.NotificationView.class)
    public Collection<NotificationDTO> findAll(Authentication authentication, @PathVariable("page") int page){
        return notificationService.findAllNotificationForUser(authentication, page);
    }

    @GetMapping("/notificationCounter")
    @PreAuthorize("not isAnonymous()")
    public double notificationCounter(){
        return notificationService.getNotificationCounter();
    }

    @MessageMapping("/test")
    public void readNotification(@Payload NotificationRequest notificationRequest){
        this.notificationService.readNotification(notificationRequest);
    }
}
