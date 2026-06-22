package com.campusplacement.service.impl;

import com.campusplacement.dto.request.ApplicationStatusRequest;
import com.campusplacement.dto.response.ApplicationStatusResponse;
import com.campusplacement.entity.Application;
import com.campusplacement.entity.Job;
import com.campusplacement.entity.Recruiter;
import com.campusplacement.entity.User;
import com.campusplacement.enums.Role;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.ApplicationManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationManagementServiceImpl
        implements ApplicationManagementService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    @Override
    public ApplicationStatusResponse updateApplicationStatus(
            ApplicationStatusRequest request) {

        User currentUser = getCurrentUser();
        validateRecruiterRole(currentUser);

        Application application = applicationRepository
                .findById(request.getApplicationId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with id: "
                                        + request.getApplicationId()
                        )
                );

        Recruiter recruiter = recruiterRepository
                .findByUserId(currentUser.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found"
                        )
                );

        if (!application.getJob()
                .getRecruiter()
                .getId()
                .equals(recruiter.getId())) {

            throw new RuntimeException(
                    "You are not authorized to update this application"
            );
        }

        application.setApplicationStatus(
                request.getApplicationStatus()
        );

        Application updatedApplication =
                applicationRepository.save(application);

        return mapToResponse(updatedApplication);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationStatusResponse> getApplicationsByJob(
            Long jobId) {

        User currentUser = getCurrentUser();
        validateRecruiterRole(currentUser);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job not found with id: " + jobId
                        )
                );

        Recruiter recruiter = recruiterRepository
                .findByUserId(currentUser.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found"
                        )
                );

        if (!job.getRecruiter()
                .getId()
                .equals(recruiter.getId())) {

            throw new RuntimeException(
                    "You are not authorized to view applications for this job"
            );
        }

        return applicationRepository
                .findByJobIdOrderByAppliedAtDesc(jobId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationStatusResponse getApplicationById(
            Long applicationId) {

        User currentUser = getCurrentUser();
        validateRecruiterRole(currentUser);

        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with id: "
                                        + applicationId
                        )
                );

        Recruiter recruiter = recruiterRepository
                .findByUserId(currentUser.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found"
                        )
                );

        if (!application.getJob()
                .getRecruiter()
                .getId()
                .equals(recruiter.getId())) {

            throw new RuntimeException(
                    "You are not authorized to view this application"
            );
        }

        return mapToResponse(application);
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
                                "Authenticated user not found: "
                                        + email
                        )
                );
    }

    private ApplicationStatusResponse mapToResponse(
            Application application) {

        return ApplicationStatusResponse.builder()
                .applicationId(application.getId())
                .studentName(
                        application.getStudent()
                                .getUser()
                                .getFullName()
                )
                .jobTitle(
                        application.getJob()
                                .getJobTitle()
                )
                .applicationStatus(
                        application.getApplicationStatus() != null
                                ? application.getApplicationStatus()
                                .getDisplayName()
                                : null
                )
                .build();
    }
}