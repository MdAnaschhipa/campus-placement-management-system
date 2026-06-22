package com.campusplacement.service.impl;

import com.campusplacement.dto.response.RecruiterDashboardResponse;
import com.campusplacement.entity.Recruiter;
import com.campusplacement.entity.User;
import com.campusplacement.enums.ApplicationStatus;
import com.campusplacement.enums.JobStatus;
import com.campusplacement.enums.Role;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.InterviewRepository;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.RecruiterDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruiterDashboardServiceImpl implements RecruiterDashboardService {

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;

    @Override
    public RecruiterDashboardResponse getDashboard() {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Recruiter recruiter = recruiterRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Recruiter profile not found"));

        Long totalJobsPosted =
                jobRepository.countByRecruiterId(recruiter.getId());

        Long activeJobs =
                jobRepository.countByRecruiterIdAndJobStatus(
                        recruiter.getId(),
                        JobStatus.OPEN
                );

        Long closedJobs =
                jobRepository.countByRecruiterIdAndJobStatus(
                        recruiter.getId(),
                        JobStatus.CLOSED
                );

        Long totalApplications =
                applicationRepository.countByJobRecruiterId(
                        recruiter.getId()
                );

        Long selectedCandidates =
                applicationRepository.countByJobRecruiterIdAndApplicationStatus(
                        recruiter.getId(),
                        ApplicationStatus.SELECTED
                );

        Long rejectedCandidates =
                applicationRepository.countByJobRecruiterIdAndApplicationStatus(
                        recruiter.getId(),
                        ApplicationStatus.REJECTED
                );

        Long scheduledInterviews =
                interviewRepository.countByApplicationJobRecruiterId(
                        recruiter.getId()
                );

        return RecruiterDashboardResponse.builder()
                .totalJobsPosted(totalJobsPosted)
                .activeJobs(activeJobs)
                .closedJobs(closedJobs)
                .totalApplications(totalApplications)
                .selectedCandidates(selectedCandidates)
                .rejectedCandidates(rejectedCandidates)
                .scheduledInterviews(scheduledInterviews)
                .build();
    }

    private void validateRecruiterRole(User user) {

        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException(
                    "Only recruiters can perform this action"
            );
        }
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "No authenticated user found in security context."
            );
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found: " + email
                        )
                );
    }
}