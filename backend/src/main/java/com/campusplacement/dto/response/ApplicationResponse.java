package com.campusplacement.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private Long studentId;

    private String studentName;

    private String applicationStatus;

    private LocalDateTime appliedAt;
}