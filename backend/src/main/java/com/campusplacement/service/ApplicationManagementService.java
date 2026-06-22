package com.campusplacement.service;

import com.campusplacement.dto.request.ApplicationStatusRequest;
import com.campusplacement.dto.response.ApplicationStatusResponse;

import java.util.List;

public interface ApplicationManagementService {

    ApplicationStatusResponse updateApplicationStatus(
            ApplicationStatusRequest request
    );

    List<ApplicationStatusResponse> getApplicationsByJob(
            Long jobId
    );

    ApplicationStatusResponse getApplicationById(
            Long applicationId
    );
}