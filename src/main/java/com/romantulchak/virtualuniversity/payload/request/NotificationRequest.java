package com.romantulchak.virtualuniversity.payload.request;

public class NotificationRequest {
    private long notificationId;
    private String username;

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
