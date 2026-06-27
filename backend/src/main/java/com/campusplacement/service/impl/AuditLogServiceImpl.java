package com.campusplacement.service.impl;

import com.campusplacement.entity.AuditLog;
import com.campusplacement.enums.AuditAction;
import com.campusplacement.enums.AuditEntityType;
import com.campusplacement.repository.AuditLogRepository;
import com.campusplacement.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void createLog(
            Long adminId,
            String adminEmail,
            AuditAction action,
            AuditEntityType entityType,
            Long entityId,
            String description) {

        AuditLog auditLog = AuditLog.builder()
                .adminId(adminId)
                .adminEmail(adminEmail)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .description(description)
                .build();

        auditLogRepository.save(auditLog);
    }
}