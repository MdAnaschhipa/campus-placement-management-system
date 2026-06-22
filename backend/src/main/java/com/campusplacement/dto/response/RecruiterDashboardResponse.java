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
public class RecruiterDashboardResponse {

    private Long totalJobsPosted;

    private Long activeJobs;

    private Long closedJobs;

    private Long totalApplications;

    private Long selectedCandidates;

    private Long rejectedCandidates;

    private Long scheduledInterviews;
}