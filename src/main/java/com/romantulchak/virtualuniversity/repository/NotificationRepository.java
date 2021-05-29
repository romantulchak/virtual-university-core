package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT n FROM Notification n LEFT OUTER JOIN n.notificationBox nn WHERE nn.id = :notificationBoxId ORDER BY n.id DESC ")
    Page<Notification> findALlNotificationForUser(@Param("notificationBoxId") long notificationBoxId, Pageable pageable);

    @Query(value = "SELECT count(n.id) FROM Notification n WHERE n.notificationBox.id = :id AND n.read = FALSE")
    double countNotReadNotification(long id);

    @Modifying
    @Query(value = "UPDATE Notification n set n.read = true WHERE n.id = :id")
    void readNotification(@Param("id") long id);

    boolean existsByIdAndReadIsFalse(long id);
}

