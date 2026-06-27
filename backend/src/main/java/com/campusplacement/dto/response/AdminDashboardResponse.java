package com.campusplacement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardResponse {

    private Long totalStudents;
    private Long totalRecruiters;
    private Long pendingRecruiters;
    private Long verifiedRecruiters;
    private Long totalJobs;
    private Long activeJobs;
    private Long closedJobs;
    private Long totalApplications;
}