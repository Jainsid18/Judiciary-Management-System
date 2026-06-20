package com.siddhant.Judiciary_Management_System.hearing.controller;

import com.siddhant.Judiciary_Management_System.hearing.dto.HearingResponse;
import com.siddhant.Judiciary_Management_System.hearing.dto.ScheduleHearingRequest;
import com.siddhant.Judiciary_Management_System.hearing.service.HearingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hearings")
@RequiredArgsConstructor
public class HearingController {

    private final HearingService hearingService;

    @PreAuthorize("hasAuthority('JUDGE')")
    @PostMapping
    public String scheduleHearing(@RequestBody ScheduleHearingRequest request) {

        return hearingService.scheduleHearing(request);
    }

    @GetMapping("/case/{caseId}")
    public List<HearingResponse> getHearings(@PathVariable Long caseId) {

        return hearingService.getHearingsByCase(caseId);
    }
}
