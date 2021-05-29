package com.romantulchak.virtualuniversity.exception;

public class NotificationNotFound extends RuntimeException {
    public NotificationNotFound(){
        super("Notification not found");
    }
}
