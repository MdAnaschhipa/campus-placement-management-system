package com.campusplacement.service;

import com.campusplacement.dto.response.JobModerationResponse;

import java.util.List;

public interface AdminJobModerationService {

    List<JobModerationResponse> getAllJobs();

    JobModerationResponse getJobById(Long jobId);

    void closeJob(Long jobId);

    void deleteJob(Long jobId);
}