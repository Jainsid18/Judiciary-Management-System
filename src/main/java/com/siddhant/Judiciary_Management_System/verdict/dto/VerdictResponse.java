package com.siddhant.Judiciary_Management_System.verdict.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VerdictResponse {

    private Long id;

    private Long caseId;

    private String verdictText;

    private String judgeEmail;

    private LocalDateTime verdictDate;
}
