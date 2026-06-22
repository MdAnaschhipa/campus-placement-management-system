package com.campusplacement.service;

import com.campusplacement.dto.request.JobRequest;
import com.campusplacement.dto.response.JobResponse;

import java.util.List;

public interface JobService {

    JobResponse createJob(
            JobRequest request
    );

    JobResponse updateJob(
            Long jobId,
            JobRequest request
    );

    void deleteJob(
            Long jobId
    );

    JobResponse getJobById(
            Long jobId
    );

    List<JobResponse> getRecruiterJobs();

    JobResponse closeJob(
            Long jobId
    );
}