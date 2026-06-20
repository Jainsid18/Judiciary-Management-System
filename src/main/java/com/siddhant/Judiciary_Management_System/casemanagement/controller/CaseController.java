package com.siddhant.Judiciary_Management_System.casemanagement.controller;

import com.siddhant.Judiciary_Management_System.casemanagement.dto.CaseResponse;
import com.siddhant.Judiciary_Management_System.casemanagement.dto.CreateCaseRequest;
import com.siddhant.Judiciary_Management_System.casemanagement.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @PreAuthorize("hasAuthority('CITIZEN')")
    @PostMapping
    public String createCase(@RequestBody CreateCaseRequest request) {

        String email = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return caseService.createCase(request, email);
    }

    @GetMapping
    public List<CaseResponse> getAllCases() {
        return caseService.getAllCases();
    }

    @GetMapping("/{id}")
    public CaseResponse getCaseById(@PathVariable Long id) {

        return caseService.getCaseById(id);
    }

    @GetMapping("/my")
    public List<CaseResponse> getMyCases() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return caseService.getCitizenCases(email);
    }
}
