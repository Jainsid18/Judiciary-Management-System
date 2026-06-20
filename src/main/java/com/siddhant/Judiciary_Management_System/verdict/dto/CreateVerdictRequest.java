package com.siddhant.Judiciary_Management_System.verdict.dto;

import lombok.Data;

@Data
public class CreateVerdictRequest {

    private Long caseId;

    private String verdictText;
}
