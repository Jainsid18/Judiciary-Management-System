package com.siddhant.Judiciary_Management_System.timeline.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CaseEventResponse {

    private String eventType;

    private String description;

    private LocalDateTime eventTime;
}
