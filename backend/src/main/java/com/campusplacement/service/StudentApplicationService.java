package com.campusplacement.service;

import com.campusplacement.dto.response.ApplyJobResponse;

public interface StudentApplicationService {

    ApplyJobResponse applyJob(Long studentId, Long jobId);
}
