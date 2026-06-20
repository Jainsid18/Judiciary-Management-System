package com.siddhant.Judiciary_Management_System.casemanagement.repository;

import com.siddhant.Judiciary_Management_System.casemanagement.entity.CourtCase;
import com.siddhant.Judiciary_Management_System.casemanagement.enums.CaseStatus;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourtCaseRepository extends JpaRepository<CourtCase,Long> {
    List<CourtCase> findByAssignedJudge(User judge);
    long countByStatus(CaseStatus status);
    List<CourtCase> findByCitizen(User citizen);
}
