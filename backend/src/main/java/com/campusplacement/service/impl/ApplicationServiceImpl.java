package com.campusplacement.service.impl;

import com.campusplacement.dto.request.ApplicationRequest;
import com.campusplacement.dto.response.ApplicationResponse;
import com.campusplacement.entity.Application;
import com.campusplacement.entity.Job;
import com.campusplacement.enums.Role;
import com.campusplacement.entity.Student;
import com.campusplacement.entity.User;
import com.campusplacement.enums.JobStatus;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public ApplicationResponse applyForJob(ApplicationRequest request) {

        User user = getCurrentUser();
        validateStudentRole(user);

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found. Please create your profile first."));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + request.getJobId()));

        if (applicationRepository.existsByStudentIdAndJobId(student.getId(), job.getId())) {
            throw new RuntimeException("You have already applied for this job");
        }

        if (job.getJobStatus() != JobStatus.OPEN) {
            throw new RuntimeException("This job is not currently open for applications");
        }

        Application application = Application.builder()
                .student(student)
                .job(job)
                .build();

        Application savedApplication = applicationRepository.save(application);

        return mapToResponse(savedApplication);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getMyApplications() {

        User user = getCurrentUser();
        validateStudentRole(user);

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found. Please create your profile first."));

        return applicationRepository
                .findByStudentIdOrderByAppliedAtDesc(student.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void validateStudentRole(User user) {
        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can perform this action");
        }
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
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

    private ApplicationResponse mapToResponse(Application application) {

        return ApplicationResponse.builder()
                .id(application.getId())
                .jobId(application.getJob().getId())
                .jobTitle(application.getJob().getJobTitle())
                .studentId(application.getStudent().getId())
                .studentName(application.getStudent().getUser().getFullName())
                .applicationStatus(application.getApplicationStatus() != null
                        ? application.getApplicationStatus().getDisplayName()
                        : null)
                .appliedAt(application.getAppliedAt())
                .build();
    }
}