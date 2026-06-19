package com.campusplacement.service;

import com.campusplacement.dto.request.RecruiterProfileRequest;
import com.campusplacement.dto.response.RecruiterProfileResponse;

public interface RecruiterService {

    RecruiterProfileResponse getProfile();

    RecruiterProfileResponse createProfile(
            RecruiterProfileRequest request
    );

    RecruiterProfileResponse updateProfile(
            RecruiterProfileRequest request
    );
}