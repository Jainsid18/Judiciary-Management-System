package com.siddhant.Judiciary_Management_System.verdict.service;

import com.siddhant.Judiciary_Management_System.casemanagement.entity.CourtCase;
import com.siddhant.Judiciary_Management_System.casemanagement.enums.CaseStatus;
import com.siddhant.Judiciary_Management_System.casemanagement.repository.CourtCaseRepository;
import com.siddhant.Judiciary_Management_System.notification.service.NotificationService;
import com.siddhant.Judiciary_Management_System.timeline.service.TimelineService;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import com.siddhant.Judiciary_Management_System.user.repository.UserRepository;
import com.siddhant.Judiciary_Management_System.verdict.dto.CreateVerdictRequest;
import com.siddhant.Judiciary_Management_System.verdict.dto.VerdictResponse;
import com.siddhant.Judiciary_Management_System.verdict.entity.Verdict;
import com.siddhant.Judiciary_Management_System.verdict.repository.VerdictRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerdictService {

    private final CourtCaseRepository caseRepository;
    private final UserRepository userRepository;
    private final VerdictRepository verdictRepository;
    private  final NotificationService notificationService;

    private final TimelineService timelineService;

    @Transactional
    public String createVerdict(CreateVerdictRequest request, String judgeEmail) {

        CourtCase courtCase = caseRepository.findById(request.getCaseId())
                        .orElseThrow(() ->
                                new RuntimeException("Case Not Found"));

        User judge = userRepository.findByEmail(judgeEmail)
                        .orElseThrow();

        if(courtCase.getAssignedJudge() == null || !courtCase.getAssignedJudge().getId().equals(judge.getId())) {

            throw new RuntimeException("You are not assigned to this case");
        }

        Verdict verdict = Verdict.builder()
                .courtCase(courtCase)
                .judge(judge)
                .verdictText(request.getVerdictText())
                .verdictDate(LocalDateTime.now())
                .build();

        verdictRepository.save(verdict);

        courtCase.setStatus(CaseStatus.VERDICT_GIVEN);
        caseRepository.save(courtCase);

        notificationService.createNotification(
                courtCase.getCitizen(),
                "Verdict has been issued for Case #" + courtCase.getId()
        );

        timelineService.addEvent(courtCase, "VERDICT_ISSUED", "Verdict submitted");

        return "Verdict Submitted Successfully";
    }

    public VerdictResponse getVerdict(Long caseId) {

        Verdict verdict = verdictRepository
                        .findByCourtCaseId(caseId)
                        .orElseThrow(() -> new RuntimeException("Verdict Not Found"));

        return VerdictResponse.builder()
                .id(verdict.getId())
                .caseId(caseId)
                .judgeEmail(verdict.getJudge().getEmail())
                .verdictText(verdict.getVerdictText())
                .verdictDate(verdict.getVerdictDate())
                .build();
    }
}
