package com.siddhant.Judiciary_Management_System.notification.repository;

import com.siddhant.Judiciary_Management_System.notification.entity.Notification;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByUserOrderByCreatedAtDesc(User user);

}
