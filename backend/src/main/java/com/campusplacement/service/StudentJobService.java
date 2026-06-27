package com.campusplacement.service;

import com.campusplacement.dto.response.JobDiscoveryResponse;

import java.util.List;

public interface StudentJobService {

    List<JobDiscoveryResponse> getAvailableJobs(Long studentId);
}