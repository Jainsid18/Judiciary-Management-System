package com.siddhant.Judiciary_Management_System.casemanagement.entity;

import com.siddhant.Judiciary_Management_System.casemanagement.enums.CaseStatus;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "court_cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private String caseType;

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private User citizen;

    @ManyToOne
    @JoinColumn(name = "judge_id")
    private User assignedJudge;

    private LocalDateTime filingDate;
}
