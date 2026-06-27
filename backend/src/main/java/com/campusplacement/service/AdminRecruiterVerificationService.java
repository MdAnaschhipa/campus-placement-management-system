package com.campusplacement.service;

import com.campusplacement.dto.response.RecruiterVerificationResponse;

import java.util.List;

public interface AdminRecruiterVerificationService {

    List<RecruiterVerificationResponse> getPendingRecruiters();

    RecruiterVerificationResponse getRecruiterDetails(
            Long recruiterId
    );

    void approveRecruiter(
            Long recruiterId
    );

    void rejectRecruiter(
            Long recruiterId
    );
}