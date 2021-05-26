package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Notification;
import com.romantulchak.virtualuniversity.model.NotificationBox;
import com.romantulchak.virtualuniversity.model.Views;

import java.time.LocalDateTime;

public class NotificationDTO {

    @JsonView(Views.NotificationView.class)
    private long id;

    @JsonView(Views.NotificationView.class)
    private String message;

    private NotificationBox notificationBox;

    @JsonView(Views.NotificationView.class)
    private LocalDateTime createdAt;

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.notificationBox = notification.getNotificationBox();
        this.createdAt = notification.getCreatedAt();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationBox getNotificationBox() {
        return notificationBox;
    }

    public void setNotificationBox(NotificationBox notificationBox) {
        this.notificationBox = notificationBox;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
