package com.campusplacement.service;

import com.campusplacement.dto.request.ApplicationRequest;
import com.campusplacement.dto.response.ApplicationResponse;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse applyForJob(
            ApplicationRequest request
    );

    List<ApplicationResponse> getMyApplications();
}