package com.siddhant.Judiciary_Management_System.verdict.entity;

import com.siddhant.Judiciary_Management_System.casemanagement.entity.CourtCase;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "verdicts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Verdict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String verdictText;

    private LocalDateTime verdictDate;

    @OneToOne
    @JoinColumn(name = "case_id")
    private CourtCase courtCase;

    @ManyToOne
    @JoinColumn(name = "judge_id")
    private User judge;
}
