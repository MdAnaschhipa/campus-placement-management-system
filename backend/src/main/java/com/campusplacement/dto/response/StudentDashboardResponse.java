package com.campusplacement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDashboardResponse {

    private String studentName;

    private Integer profileCompletionPercentage;

    private Boolean eligibleToApply;

    private Long totalApplications;

    private Long selectedApplications;

    private Long rejectedApplications;

    private Long availableJobs;
}