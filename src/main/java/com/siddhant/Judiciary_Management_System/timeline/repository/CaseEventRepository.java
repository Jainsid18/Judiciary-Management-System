package com.siddhant.Judiciary_Management_System.timeline.repository;

import com.siddhant.Judiciary_Management_System.timeline.entity.CaseEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseEventRepository extends JpaRepository<CaseEvent,Long> {

    List<CaseEvent> findByCourtCaseIdOrderByEventTimeAsc(Long caseId);

}
