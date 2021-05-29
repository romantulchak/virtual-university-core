package com.romantulchak.virtualuniversity.model;

public interface Resource {

    String NOTIFICATION_COUNTER_DESTINATION = "/queue/notification";
    String NOTIFICATION_SNAC_DESTINATION = "/queue/notification-snac";
    String NOTIFICATION_READ = "/topic/read-notification";

}
