package com.siddhant.Judiciary_Management_System.dashboard.service;

import com.siddhant.Judiciary_Management_System.casemanagement.enums.CaseStatus;
import com.siddhant.Judiciary_Management_System.casemanagement.repository.CourtCaseRepository;
import com.siddhant.Judiciary_Management_System.dashboard.dto.DashboardResponse;
import com.siddhant.Judiciary_Management_System.user.enums.Role;
import com.siddhant.Judiciary_Management_System.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CourtCaseRepository caseRepository;
    private final UserRepository userRepository;

    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()
                .totalCases(caseRepository.count())
                .openCases(caseRepository.count() - caseRepository.countByStatus(
                                CaseStatus.CLOSED))
                .closedCases(caseRepository.countByStatus(CaseStatus.CLOSED))
                .totalJudges(userRepository.countByRole(Role.JUDGE))
                .build();
    }
}
