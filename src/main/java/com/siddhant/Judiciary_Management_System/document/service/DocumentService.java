package com.siddhant.Judiciary_Management_System.document.service;

import com.siddhant.Judiciary_Management_System.casemanagement.entity.CourtCase;
import com.siddhant.Judiciary_Management_System.casemanagement.repository.CourtCaseRepository;
import com.siddhant.Judiciary_Management_System.document.dto.DocumentResponse;
import com.siddhant.Judiciary_Management_System.document.entity.CaseDocument;
import com.siddhant.Judiciary_Management_System.document.repository.CaseDocumentRepository;
import com.siddhant.Judiciary_Management_System.timeline.service.TimelineService;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import com.siddhant.Judiciary_Management_System.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final CourtCaseRepository caseRepository;
    private final UserRepository userRepository;
    private final CaseDocumentRepository documentRepository;

    private final TimelineService timelineService;
    @Transactional
    public String uploadDocument(Long caseId, MultipartFile file, String email) throws IOException {

        CourtCase courtCase = caseRepository.findById(caseId)
                        .orElseThrow(() -> new RuntimeException("Case Not Found"));

        User user = userRepository.findByEmail(email)
                        .orElseThrow();

        String fileName = UUID.randomUUID()
                        + "_"
                        + file.getOriginalFilename();

        Path path = Paths.get("uploads", fileName);

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        CaseDocument document = CaseDocument.builder()
                        .fileName(file.getOriginalFilename())
                        .fileType(file.getContentType())
                        .filePath(path.toString())
                        .uploadedAt(LocalDateTime.now())
                        .courtCase(courtCase)
                        .uploadedBy(user)
                        .build();

        timelineService.addEvent(courtCase, "DOCUMENT_UPLOADED", file.getOriginalFilename());
        documentRepository.save(document);

        return "Document Uploaded Successfully";
    }

    public List<DocumentResponse> getCaseDocuments(Long caseId) {

        List<CaseDocument> documents = documentRepository.findByCourtCaseId(caseId);

        return documents.stream()
                .map(document -> DocumentResponse.builder()
                                .id(document.getId())
                                .fileName(document.getFileName())
                                .fileType(document.getFileType())
                                .uploadedBy(document.getUploadedBy()
                                                .getEmail())
                                .uploadedAt(document.getUploadedAt())
                                .build()
                )
                .toList();
    }
}
