package com.campusplacement.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentManagementResponse {

    private Long studentId;
    private Long userId;
    private String fullName;
    private String email;
    private String enrollmentNumber;
    private String branchName;
    private Integer semester;
    private Double cgpa;
    private Boolean active;
    private LocalDateTime createdAt;
}