package com.siddhant.Judiciary_Management_System.hearing.repository;

import com.siddhant.Judiciary_Management_System.hearing.entity.Hearing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HearingRepository extends JpaRepository<Hearing,Long> {

    List<Hearing> findByCourtCaseId(Long caseId);

}
