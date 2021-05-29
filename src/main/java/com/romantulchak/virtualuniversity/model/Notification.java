package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;

    @ManyToOne
    private NotificationBox notificationBox;

    private LocalDateTime createdAt;

    private boolean read;

    public Notification(String message, NotificationBox notificationBox) {
        this.message = message;
        this.notificationBox = notificationBox;
        this.createdAt = LocalDateTime.now();
    }

    public Notification() {

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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
