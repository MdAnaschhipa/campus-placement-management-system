package com.campusplacement.service.impl;

import com.campusplacement.dto.response.JobModerationResponse;
import com.campusplacement.entity.Job;
import com.campusplacement.entity.User;
import com.campusplacement.enums.AuditAction;
import com.campusplacement.enums.AuditEntityType;
import com.campusplacement.enums.JobStatus;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.AdminJobModerationService;
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
public class AdminJobModerationServiceImpl
        implements AdminJobModerationService {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @Override
    public List<JobModerationResponse> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public JobModerationResponse getJobById(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        return mapToResponse(job);
    }

    @Override
    public void closeJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        job.setJobStatus(JobStatus.CLOSED);

        jobRepository.save(job);

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
                AuditAction.CLOSE_JOB,
                AuditEntityType.JOB,
                job.getId(),
                "Closed job: "
                        + job.getJobTitle()
        );
    }

    @Override
    public void deleteJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

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
                AuditAction.DELETE_JOB,
                AuditEntityType.JOB,
                job.getId(),
                "Deleted job: "
                        + job.getJobTitle()
        );

        jobRepository.delete(job);
    }

    private JobModerationResponse mapToResponse(Job job) {

        return JobModerationResponse.builder()
                .jobId(job.getId())
                .jobTitle(job.getJobTitle())
                .companyName(job.getRecruiter().getCompanyName())
                .jobType(
                        job.getJobType() != null
                                ? job.getJobType().name()
                                : null
                )
                .location(job.getLocation())
                .salaryPackage(job.getSalaryPackage())
                .jobStatus(
                        job.getJobStatus() != null
                                ? job.getJobStatus().name()
                                : null
                )
                .totalApplications(
                        (int) applicationRepository.countByJobId(
                                job.getId()
                        )
                )
                .applicationDeadline(
                        job.getApplicationDeadline()
                )
                .createdAt(job.getCreatedAt())
                .build();
    }
}