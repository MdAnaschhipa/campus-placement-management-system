package com.campusplacement.service.impl;

import com.campusplacement.dto.response.AdminDashboardResponse;
import com.campusplacement.enums.Role;
import com.campusplacement.enums.JobStatus;
import com.campusplacement.enums.VerificationStatus;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserRepository userRepository;
    private final RecruiterRepository recruiterRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public AdminDashboardResponse getDashboard() {

        return AdminDashboardResponse.builder()
                .totalStudents(userRepository.countByRole(Role.STUDENT))
                .totalRecruiters(userRepository.countByRole(Role.RECRUITER))
                .pendingRecruiters(
                        recruiterRepository.countByVerificationStatus(
                                VerificationStatus.PENDING
                        )
                )
                .verifiedRecruiters(
                        recruiterRepository.countByVerificationStatus(
                                VerificationStatus.VERIFIED
                        )
                )
                .totalJobs(jobRepository.count())
                .activeJobs(jobRepository.countByJobStatus(JobStatus.OPEN))
                .closedJobs(jobRepository.countByJobStatus(JobStatus.CLOSED))
                .totalApplications(applicationRepository.count())
                .build();
    }
}