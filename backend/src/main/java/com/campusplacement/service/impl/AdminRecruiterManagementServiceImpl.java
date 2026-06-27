package com.campusplacement.service.impl;

import com.campusplacement.dto.response.RecruiterManagementResponse;
import com.campusplacement.entity.Recruiter;
import com.campusplacement.entity.User;
import com.campusplacement.enums.AuditAction;
import com.campusplacement.enums.AuditEntityType;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.AdminRecruiterManagementService;
import com.campusplacement.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminRecruiterManagementServiceImpl
        implements AdminRecruiterManagementService {

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @Override
    @Transactional(readOnly = true)
    public List<RecruiterManagementResponse> getAllRecruiters() {

        return recruiterRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecruiterManagementResponse getRecruiterById(
            Long recruiterId
    ) {

        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));

        return mapToResponse(recruiter);
    }

    @Override
    public void activateRecruiter(Long recruiterId) {

        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));

        recruiter.getUser().setActive(true);

        recruiterRepository.save(recruiter);

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String adminEmail = authentication.getName();

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        auditLogService.createLog(
                admin.getId(),
                admin.getEmail(),
                AuditAction.ACTIVATE_RECRUITER,
                AuditEntityType.RECRUITER,
                recruiter.getId(),
                "Activated recruiter: "
                        + recruiter.getCompanyName()
        );
    }

    @Override
    public void deactivateRecruiter(Long recruiterId) {

        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));

        recruiter.getUser().setActive(false);

        recruiterRepository.save(recruiter);

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String adminEmail = authentication.getName();

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        auditLogService.createLog(
                admin.getId(),
                admin.getEmail(),
                AuditAction.DEACTIVATE_RECRUITER,
                AuditEntityType.RECRUITER,
                recruiter.getId(),
                "Deactivated recruiter: "
                        + recruiter.getCompanyName()
        );
    }

    private RecruiterManagementResponse mapToResponse(
            Recruiter recruiter
    ) {

        return RecruiterManagementResponse.builder()
                .recruiterId(recruiter.getId())
                .userId(recruiter.getUser().getId())
                .companyName(recruiter.getCompanyName())
                .officialCompanyEmail(
                        recruiter.getOfficialCompanyEmail()
                )
                .contactPersonName(
                        recruiter.getContactPersonName()
                )
                .verificationStatus(
                        recruiter.getVerificationStatus() != null
                                ? recruiter.getVerificationStatus().name()
                                : null
                )
                .active(recruiter.getUser().isActive())
                .createdAt(recruiter.getCreatedAt())
                .build();
    }
}