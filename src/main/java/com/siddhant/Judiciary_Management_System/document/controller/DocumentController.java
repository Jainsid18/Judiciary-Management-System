package com.siddhant.Judiciary_Management_System.document.controller;

import com.siddhant.Judiciary_Management_System.document.dto.DocumentResponse;
import com.siddhant.Judiciary_Management_System.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload/{caseId}")
    public String uploadDocument(@PathVariable Long caseId, @RequestParam("file") MultipartFile file) throws IOException {

        String email = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return documentService.uploadDocument(caseId, file, email);
    }

    @GetMapping("/case/{caseId}")
    public List<DocumentResponse> getCaseDocuments(@PathVariable Long caseId) {

        return documentService.getCaseDocuments(caseId);
    }
}
