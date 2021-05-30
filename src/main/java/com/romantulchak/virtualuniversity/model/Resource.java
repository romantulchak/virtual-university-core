package com.romantulchak.virtualuniversity.model;

public interface Resource {

    String NOTIFICATION_COUNTER_DESTINATION = "/queue/notification";
    String NOTIFICATION_SNAC_DESTINATION = "/queue/notification-snac";
    String NOTIFICATION_READ = "/topic/read-notification";
    String NOTIFICATION_SCHEDULE_CHANGED = "/queue/schedule-changed";

    String MSG_REQUEST_REJECTED = "Your request for %s  Subject: %s was rejected";
    String MSG_REQUEST_ACCEPTED = "Your request for %s  Subject: %s was accepted";
    String MSG_SCHEDULE_CHANGED = "Schedule for day %s was changed";

}
