package com.siddhant.Judiciary_Management_System.verdict.controller;

import com.siddhant.Judiciary_Management_System.verdict.dto.CreateVerdictRequest;
import com.siddhant.Judiciary_Management_System.verdict.dto.VerdictResponse;
import com.siddhant.Judiciary_Management_System.verdict.service.VerdictService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verdicts")
@RequiredArgsConstructor
public class VerdictController {

    private final VerdictService verdictService;

    @PreAuthorize("hasAuthority('JUDGE')")
    @PostMapping
    public String createVerdict(@RequestBody CreateVerdictRequest request) {

        String email = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return verdictService.createVerdict(request,email);
    }

    @GetMapping("/{caseId}")
    public VerdictResponse getVerdict(@PathVariable Long caseId) {

        return verdictService.getVerdict(caseId);
    }
}
