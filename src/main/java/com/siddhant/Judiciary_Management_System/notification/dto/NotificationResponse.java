package com.siddhant.Judiciary_Management_System.notification.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {

    private Long id;

    private String message;

    private boolean isRead;

    private LocalDateTime createdAt;
}
