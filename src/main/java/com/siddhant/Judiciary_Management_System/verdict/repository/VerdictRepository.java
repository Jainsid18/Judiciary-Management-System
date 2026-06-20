package com.siddhant.Judiciary_Management_System.verdict.repository;

import com.siddhant.Judiciary_Management_System.verdict.entity.Verdict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerdictRepository extends JpaRepository<Verdict,Long> {

    Optional<Verdict> findByCourtCaseId(Long caseId);
}
