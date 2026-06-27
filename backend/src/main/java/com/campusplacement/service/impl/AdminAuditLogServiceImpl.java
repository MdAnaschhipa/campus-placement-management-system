package com.campusplacement.service.impl;

import com.campusplacement.dto.response.AuditLogResponse;
import com.campusplacement.entity.AuditLog;
import com.campusplacement.enums.AuditAction;
import com.campusplacement.repository.AuditLogRepository;
import com.campusplacement.service.AdminAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminAuditLogServiceImpl implements AdminAuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public List<AuditLogResponse> getAllLogs() {

        return auditLogRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AuditLogResponse getLogById(Long logId) {

        AuditLog auditLog = auditLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Audit log not found"));

        return mapToResponse(auditLog);
    }

    @Override
    public List<AuditLogResponse> getLogsByAdmin(Long adminId) {

        return auditLogRepository.findByAdminIdOrderByCreatedAtDesc(adminId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AuditLogResponse> getLogsByAction(AuditAction action) {

        return auditLogRepository.findByActionOrderByCreatedAtDesc(action)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AuditLogResponse mapToResponse(AuditLog auditLog) {

        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .adminId(auditLog.getAdminId())
                .adminEmail(auditLog.getAdminEmail())
                .action(auditLog.getAction() != null
                        ? auditLog.getAction().name()
                        : null)
                .entityType(auditLog.getEntityType() != null
                        ? auditLog.getEntityType().name()
                        : null)
                .entityId(auditLog.getEntityId())
                .description(auditLog.getDescription())
                .createdAt(auditLog.getCreatedAt())
                .build();
    }
}