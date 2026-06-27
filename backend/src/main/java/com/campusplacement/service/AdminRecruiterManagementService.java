package com.campusplacement.service;

import com.campusplacement.dto.response.RecruiterManagementResponse;

import java.util.List;

public interface AdminRecruiterManagementService {

    List<RecruiterManagementResponse> getAllRecruiters();

    RecruiterManagementResponse getRecruiterById(
            Long recruiterId
    );

    void activateRecruiter(
            Long recruiterId
    );

    void deactivateRecruiter(
            Long recruiterId
    );
}