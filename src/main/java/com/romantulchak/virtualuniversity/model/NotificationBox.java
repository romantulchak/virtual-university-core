package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class NotificationBox {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "notificationBox")
    private Collection<Notification> notifications;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
    }

}
