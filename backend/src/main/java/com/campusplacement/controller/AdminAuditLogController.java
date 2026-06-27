package com.campusplacement.controller;

import com.campusplacement.dto.response.AuditLogResponse;
import com.campusplacement.enums.AuditAction;
import com.campusplacement.service.AdminAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/audit-logs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminAuditLogController {

    private final AdminAuditLogService adminAuditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> getAllLogs() {
        return ResponseEntity.ok(adminAuditLogService.getAllLogs());
    }

    @GetMapping("/{logId}")
    public ResponseEntity<AuditLogResponse> getLogById(@PathVariable Long logId) {
        return ResponseEntity.ok(adminAuditLogService.getLogById(logId));
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AuditLogResponse>> getLogsByAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok(adminAuditLogService.getLogsByAdmin(adminId));
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLogResponse>> getLogsByAction(@PathVariable AuditAction action) {
        return ResponseEntity.ok(adminAuditLogService.getLogsByAction(action));
    }
}