package com.siddhant.Judiciary_Management_System.hearing.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HearingResponse {

    private Long id;

    private Long caseId;

    private LocalDateTime hearingDate;

    private String remarks;
}
