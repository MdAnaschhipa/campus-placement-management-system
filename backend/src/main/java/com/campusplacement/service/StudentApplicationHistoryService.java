package com.campusplacement.service;

import com.campusplacement.dto.response.ApplicationHistoryResponse;

import java.util.List;

public interface StudentApplicationHistoryService {

    List<ApplicationHistoryResponse> getApplicationHistory(
            Long studentId
    );
}