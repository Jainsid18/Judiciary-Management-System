package com.siddhant.Judiciary_Management_System.hearing.service;

import com.siddhant.Judiciary_Management_System.casemanagement.entity.CourtCase;
import com.siddhant.Judiciary_Management_System.casemanagement.enums.CaseStatus;
import com.siddhant.Judiciary_Management_System.casemanagement.repository.CourtCaseRepository;
import com.siddhant.Judiciary_Management_System.hearing.dto.HearingResponse;
import com.siddhant.Judiciary_Management_System.hearing.dto.ScheduleHearingRequest;
import com.siddhant.Judiciary_Management_System.hearing.entity.Hearing;
import com.siddhant.Judiciary_Management_System.hearing.repository.HearingRepository;
import com.siddhant.Judiciary_Management_System.notification.service.NotificationService;
import com.siddhant.Judiciary_Management_System.timeline.service.TimelineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HearingService {

    private final HearingRepository hearingRepository;
    private final CourtCaseRepository caseRepository;

    private final NotificationService notificationService;

    private final TimelineService timelineService;

    public List<HearingResponse> getHearingsByCase(
            Long caseId) {

        return hearingRepository
                .findByCourtCaseId(caseId)
                .stream()
                .map(h -> HearingResponse.builder()
                        .id(h.getId())
                        .caseId(h.getCourtCase().getId())
                        .hearingDate(h.getHearingDate())
                        .remarks(h.getRemarks())
                        .build())
                .toList();
    }

    @Transactional
    public String scheduleHearing(ScheduleHearingRequest request) {

        CourtCase courtCase = caseRepository.findById(request.getCaseId())
                        .orElseThrow(() -> new RuntimeException("Case Not Found"));

        Hearing hearing = Hearing.builder()
                .courtCase(courtCase)
                .hearingDate(request.getHearingDate())
                .remarks(request.getRemarks())
                .build();

        hearingRepository.save(hearing);

        notificationService.createNotification(
                courtCase.getCitizen(),
                "Hearing scheduled on " + request.getHearingDate()
        );

        courtCase.setStatus(CaseStatus.HEARING_SCHEDULED);

        timelineService.addEvent(courtCase, "HEARING_SCHEDULED", "Hearing scheduled on " + hearing.getHearingDate());
        caseRepository.save(courtCase);

        return "Hearing Scheduled Successfully";
    }
}
