package com.siddhant.Judiciary_Management_System.notification.controller;

import com.siddhant.Judiciary_Management_System.notification.dto.NotificationResponse;
import com.siddhant.Judiciary_Management_System.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getMyNotifications() {

        String email = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return notificationService.getMyNotifications(email);
    }
}
