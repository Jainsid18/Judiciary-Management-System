package com.siddhant.Judiciary_Management_System.casemanagement.controller;

import com.siddhant.Judiciary_Management_System.casemanagement.dto.CaseResponse;
import com.siddhant.Judiciary_Management_System.casemanagement.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/judge")
@RequiredArgsConstructor
public class JudgeController {

    private final CaseService caseService;

    @PreAuthorize("hasAuthority('JUDGE')")
    @GetMapping("/cases")
    public List<CaseResponse> getJudgeCases() {

        String email = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return caseService.getJudgeCases(email);
    }
}
