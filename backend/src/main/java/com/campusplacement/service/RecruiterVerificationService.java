package com.campusplacement.service;

import com.campusplacement.dto.request.RecruiterVerificationRequest;
import com.campusplacement.dto.response.RecruiterVerificationResponse;
import com.campusplacement.enums.AuditAction;
import com.campusplacement.enums.AuditEntityType;
import com.campusplacement.service.AuditLogService;



public interface RecruiterVerificationService {


    RecruiterVerificationResponse submitVerification(
            RecruiterVerificationRequest request
    );

    RecruiterVerificationResponse getVerificationStatus();

}