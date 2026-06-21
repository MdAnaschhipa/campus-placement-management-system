package com.campusplacement.service;

import com.campusplacement.dto.request.RecruiterVerificationRequest;
import com.campusplacement.dto.response.RecruiterVerificationResponse;

public interface RecruiterVerificationService {

    RecruiterVerificationResponse submitVerification(
            RecruiterVerificationRequest request
    );

    RecruiterVerificationResponse getVerificationStatus();
}