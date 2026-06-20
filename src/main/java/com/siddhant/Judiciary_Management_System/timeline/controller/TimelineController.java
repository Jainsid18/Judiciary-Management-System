package com.siddhant.Judiciary_Management_System.timeline.controller;

import com.siddhant.Judiciary_Management_System.timeline.dto.CaseEventResponse;
import com.siddhant.Judiciary_Management_System.timeline.service.TimelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/timeline")
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineService timelineService;

    @GetMapping("/{caseId}")
    public List<CaseEventResponse> getTimeline(@PathVariable Long caseId) {

        return timelineService.getTimeline(caseId);
    }
}
