package com.campusplacement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterDashboardResponse {

    private Long totalJobsPosted;

    private Long activeJobs;

    private Long closedJobs;

    private Long totalApplications;
}