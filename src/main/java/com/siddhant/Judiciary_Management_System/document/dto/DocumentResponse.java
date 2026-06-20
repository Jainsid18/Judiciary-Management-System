package com.siddhant.Judiciary_Management_System.document.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DocumentResponse {

    private Long id;

    private String fileName;

    private String fileType;

    private String uploadedBy;

    private LocalDateTime uploadedAt;
}
