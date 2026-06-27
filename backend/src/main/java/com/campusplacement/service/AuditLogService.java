package com.campusplacement.service;

import com.campusplacement.enums.AuditAction;
import com.campusplacement.enums.AuditEntityType;

public interface AuditLogService {

    void createLog(
            Long adminId,
            String adminEmail,
            AuditAction action,
            AuditEntityType entityType,
            Long entityId,
            String description
    );
}