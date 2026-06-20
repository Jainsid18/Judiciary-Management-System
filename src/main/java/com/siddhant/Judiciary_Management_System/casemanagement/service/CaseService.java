package com.siddhant.Judiciary_Management_System.casemanagement.service;

import com.siddhant.Judiciary_Management_System.casemanagement.dto.CaseResponse;
import com.siddhant.Judiciary_Management_System.casemanagement.dto.CreateCaseRequest;
import com.siddhant.Judiciary_Management_System.casemanagement.entity.CourtCase;
import com.siddhant.Judiciary_Management_System.casemanagement.enums.CaseStatus;
import com.siddhant.Judiciary_Management_System.casemanagement.repository.CourtCaseRepository;
import com.siddhant.Judiciary_Management_System.notification.service.NotificationService;
import com.siddhant.Judiciary_Management_System.timeline.service.TimelineService;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import com.siddhant.Judiciary_Management_System.user.enums.Role;
import com.siddhant.Judiciary_Management_System.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseService {

    private final CourtCaseRepository caseRepository;
    private final UserRepository userRepository;
    private final TimelineService timelineService;

    private final NotificationService notificationService;

    public String createCase(CreateCaseRequest request, String email) {

        User citizen = userRepository
                .findByEmail(email)
                .orElseThrow();

        CourtCase courtCase = CourtCase.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .caseType(request.getCaseType())
                .status(CaseStatus.FILED)
                .citizen(citizen)
                .filingDate(LocalDateTime.now())
                .build();

        CourtCase savedCase = caseRepository.save(courtCase);

        timelineService.addEvent(savedCase, "CASE_FILED", "Case filed by citizen");

        caseRepository.save(courtCase);

        return "Case Filed Successfully";
    }

    public List<CaseResponse> getAllCases() {

        return caseRepository.findAll()
                .stream()
                .map(c -> CaseResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .description(c.getDescription())
                        .caseType(c.getCaseType())
                        .status(c.getStatus().name())
                        .citizenEmail(
                                c.getCitizen().getEmail())
                        .assignedJudge(
                                c.getAssignedJudge() != null
                                        ? c.getAssignedJudge().getEmail()
                                        : null
                        )
                        .build())
                .toList();
    }

    public CaseResponse getCaseById(Long id) {

        CourtCase courtCase = caseRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Case Not Found"));

        return CaseResponse.builder()
                .id(courtCase.getId())
                .title(courtCase.getTitle())
                .description(courtCase.getDescription())
                .caseType(courtCase.getCaseType())
                .status(courtCase.getStatus().name())
                .citizenEmail(
                        courtCase.getCitizen().getEmail())
                .assignedJudge(courtCase.getAssignedJudge() != null
                                ? courtCase.getAssignedJudge().getEmail()
                                : null)
                .build();
    }

    @Transactional
    public String assignJudge(Long caseId, Long judgeId) {

        CourtCase courtCase = caseRepository.findById(caseId)
                        .orElseThrow(() -> new RuntimeException("Case Not Found"));

        User judge = userRepository.findById(judgeId)
                        .orElseThrow(() -> new RuntimeException("Judge Not Found"));

        if(judge.getRole() != Role.JUDGE) {
            throw new RuntimeException("User is not a Judge");
        }

        courtCase.setAssignedJudge(judge);
        notificationService.createNotification(
                judge,"You have been assigned Case #" + courtCase.getId()
        );

        courtCase.setStatus(CaseStatus.JUDGE_ASSIGNED);

        caseRepository.save(courtCase);

        timelineService.addEvent(courtCase, "JUDGE_ASSIGNED", "Judge assigned: " + judge.getEmail());

        return "Judge Assigned Successfully";
    }

    public List<CaseResponse> getJudgeCases(String email) {
        User judge = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CourtCase> cases = caseRepository.findByAssignedJudge(judge);

        return cases.stream()
                .map(c -> CaseResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .description(c.getDescription())
                        .caseType(c.getCaseType())
                        .status(c.getStatus().name())
                        .citizenEmail(c.getCitizen().getEmail())
                        .assignedJudge(
                                c.getAssignedJudge() != null
                                        ? c.getAssignedJudge().getEmail()
                                        : null
                        )
                        .build()
                ).toList();
    }

    @Transactional
    public String closeCase(Long caseId) {

        CourtCase courtCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case Not Found"));

        if(courtCase.getStatus() != CaseStatus.VERDICT_GIVEN) {
            throw new RuntimeException("Case can only be closed after verdict");
        }

        courtCase.setStatus(CaseStatus.CLOSED);

        caseRepository.save(courtCase);

        notificationService.createNotification(courtCase.getCitizen(), "Case #" + courtCase.getId() + " has been closed");

        timelineService.addEvent(courtCase, "CASE_CLOSED", "Case closed successfully");

        return "Case Closed Successfully";
    }

    public List<CaseResponse> getCitizenCases(String email) {

        User citizen = userRepository
                .findByEmail(email)
                .orElseThrow();

        return caseRepository
                .findByCitizen(citizen)
                .stream()
                .map(c -> CaseResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .description(c.getDescription())
                        .caseType(c.getCaseType())
                        .status(c.getStatus().name())
                        .citizenEmail(c.getCitizen().getEmail())
                        .assignedJudge(
                                c.getAssignedJudge() != null
                                        ? c.getAssignedJudge().getEmail()
                                        : null)
                        .build())
                .toList();
    }
}
