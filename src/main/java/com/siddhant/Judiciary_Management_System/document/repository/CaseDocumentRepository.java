package com.siddhant.Judiciary_Management_System.document.repository;

import com.siddhant.Judiciary_Management_System.document.entity.CaseDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseDocumentRepository extends JpaRepository<CaseDocument,Long> {

    List<CaseDocument> findByCourtCaseId(Long caseId);
}
