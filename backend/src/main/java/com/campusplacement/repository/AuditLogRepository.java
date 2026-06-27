package com.campusplacement.repository;

import com.campusplacement.entity.AuditLog;
import com.campusplacement.enums.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByAdminIdOrderByCreatedAtDesc(
            Long adminId
    );

    List<AuditLog> findByActionOrderByCreatedAtDesc(
            AuditAction action
    );

    List<AuditLog> findAllByOrderByCreatedAtDesc();
}