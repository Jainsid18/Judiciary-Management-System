package com.siddhant.Judiciary_Management_System.casemanagement.dto;

import lombok.Data;

@Data
public class CreateCaseRequest {

    private String title;
    private String description;
    private String caseType;
}
