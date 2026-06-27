package com.campusplacement.service;

import com.campusplacement.dto.response.AuditLogResponse;
import com.campusplacement.enums.AuditAction;

import java.util.List;

public interface AdminAuditLogService {

    List<AuditLogResponse> getAllLogs();

    AuditLogResponse getLogById(
            Long logId
    );

    List<AuditLogResponse> getLogsByAdmin(
            Long adminId
    );

    List<AuditLogResponse> getLogsByAction(
            AuditAction action
    );
}