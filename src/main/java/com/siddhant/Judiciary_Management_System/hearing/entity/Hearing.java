package com.siddhant.Judiciary_Management_System.hearing.entity;

import com.siddhant.Judiciary_Management_System.casemanagement.entity.CourtCase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hearings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hearing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime hearingDate;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private CourtCase courtCase;
}
