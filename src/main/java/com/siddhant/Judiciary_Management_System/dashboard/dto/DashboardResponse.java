package com.siddhant.Judiciary_Management_System.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private long totalCases;
    private long openCases;
    private long closedCases;
    private long totalJudges;

}
