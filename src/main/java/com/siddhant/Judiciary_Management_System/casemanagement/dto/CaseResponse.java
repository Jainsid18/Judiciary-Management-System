package com.siddhant.Judiciary_Management_System.casemanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CaseResponse {

    private Long id;
    private String title;
    private String description;
    private String caseType;
    private String status;
    private String citizenEmail;
    private String assignedJudge;
}
