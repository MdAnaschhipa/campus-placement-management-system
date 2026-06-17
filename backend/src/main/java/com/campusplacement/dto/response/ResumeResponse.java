package com.campusplacement.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeResponse {

    private Long id;

    private String fileName;

    private Long fileSize;

    private String downloadUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}