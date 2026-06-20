package com.siddhant.Judiciary_Management_System.admin;

import com.siddhant.Judiciary_Management_System.admin.dto.CreateUserRequest;
import com.siddhant.Judiciary_Management_System.admin.service.AdminService;
import com.siddhant.Judiciary_Management_System.casemanagement.dto.AssignJudgeRequest;
import com.siddhant.Judiciary_Management_System.casemanagement.service.CaseService;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class controller {

    private final CaseService caseService;
    private final AdminService adminService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/cases/{caseId}/assign-judge/{judgeId}")
    public String assignJudge(@PathVariable Long caseId, @RequestBody AssignJudgeRequest request) {
        return caseService.assignJudge(
                caseId,
                request.getJudgeId());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users")
    public String createUser(
            @RequestBody CreateUserRequest request) {

        return adminService.createUser(request);
    }

    @PreAuthorize("hasAuthority('JUDGE')")
    @PutMapping("/cases/{caseId}/close")
    public String closeCase(@PathVariable Long caseId) {

        return caseService.closeCase(caseId);
    }

    @GetMapping("/judges")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllJudges() {

        return adminService.getAllJudges();
    }
}
