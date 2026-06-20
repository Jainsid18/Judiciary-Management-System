package com.siddhant.Judiciary_Management_System.hearing.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleHearingRequest {

    private Long caseId;

    private LocalDateTime hearingDate;

    private String remarks;
}
