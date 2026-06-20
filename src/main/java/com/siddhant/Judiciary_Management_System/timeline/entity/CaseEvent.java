package com.siddhant.Judiciary_Management_System.timeline.entity;

import com.siddhant.Judiciary_Management_System.casemanagement.entity.CourtCase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "case_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;

    private String description;

    private LocalDateTime eventTime;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private CourtCase courtCase;
}
