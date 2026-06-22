package com.campusplacement.service;

import com.campusplacement.dto.request.SelectionRoundRequest;
import com.campusplacement.dto.response.SelectionRoundResponse;

import java.util.List;

public interface SelectionRoundService {

    SelectionRoundResponse createRound(
            SelectionRoundRequest request
    );

    List<SelectionRoundResponse> getJobRounds(
            Long jobId
    );
}