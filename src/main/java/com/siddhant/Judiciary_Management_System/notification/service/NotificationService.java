package com.siddhant.Judiciary_Management_System.notification.service;

import com.siddhant.Judiciary_Management_System.notification.dto.NotificationResponse;
import com.siddhant.Judiciary_Management_System.notification.entity.Notification;
import com.siddhant.Judiciary_Management_System.notification.repository.NotificationRepository;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import com.siddhant.Judiciary_Management_System.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public void createNotification(User user, String message) {

        Notification notification = Notification.builder()
                        .message(message)
                        .isRead(false)
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .build();

        notificationRepository.save(notification);
    }

    public List<NotificationResponse> getMyNotifications(String email) {

        User user = userRepository.findByEmail(email)
                        .orElseThrow();

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(notification ->
                        NotificationResponse.builder()
                                .id(notification.getId())
                                .message(notification.getMessage())
                                .isRead(notification.isRead())
                                .createdAt(notification.getCreatedAt())
                                .build())
                .toList();
    }
}
