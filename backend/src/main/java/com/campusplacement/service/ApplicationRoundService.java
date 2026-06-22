package com.campusplacement.service;

import com.campusplacement.dto.request.ApplicationRoundRequest;
import com.campusplacement.dto.response.ApplicationRoundResponse;

import java.util.List;

public interface ApplicationRoundService {

    ApplicationRoundResponse updateRoundStatus(
            ApplicationRoundRequest request
    );

    List<ApplicationRoundResponse> getApplicationRounds(
            Long applicationId
    );
}