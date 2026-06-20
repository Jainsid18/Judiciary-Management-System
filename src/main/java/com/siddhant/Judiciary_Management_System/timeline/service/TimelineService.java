package com.siddhant.Judiciary_Management_System.timeline.service;

import com.siddhant.Judiciary_Management_System.casemanagement.entity.CourtCase;
import com.siddhant.Judiciary_Management_System.timeline.dto.CaseEventResponse;
import com.siddhant.Judiciary_Management_System.timeline.entity.CaseEvent;
import com.siddhant.Judiciary_Management_System.timeline.repository.CaseEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimelineService {

    private final CaseEventRepository caseEventRepository;

    public void addEvent(CourtCase courtCase, String eventType, String description) {

        CaseEvent event = CaseEvent.builder()
                        .courtCase(courtCase)
                        .eventType(eventType)
                        .description(description)
                        .eventTime(LocalDateTime.now())
                        .build();

        caseEventRepository.save(event);
    }

    public List<CaseEventResponse> getTimeline(Long caseId) {

        return caseEventRepository
                .findByCourtCaseIdOrderByEventTimeAsc(caseId)
                .stream()
                .map(event ->
                        CaseEventResponse.builder()
                                .eventType(event.getEventType())
                                .description(event.getDescription())
                                .eventTime(event.getEventTime())
                                .build())
                .toList();
    }
}
