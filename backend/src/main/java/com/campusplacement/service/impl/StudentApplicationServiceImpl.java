package com.campusplacement.service.impl;

import com.campusplacement.dto.response.ApplyJobResponse;
import com.campusplacement.entity.Application;
import com.campusplacement.entity.Job;
import com.campusplacement.entity.Student;
import com.campusplacement.enums.ApplicationStatus;
import com.campusplacement.enums.JobStatus;
import com.campusplacement.enums.VerificationStatus;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.service.ProfileCompletionService;
import com.campusplacement.service.StudentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentApplicationServiceImpl
        implements StudentApplicationService {

    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final ProfileCompletionService profileCompletionService;

    @Override
    public ApplyJobResponse applyJob(
            Long studentId,
            Long jobId
    ) {

        // Rule 1: Student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"));

        // Rule 2: Job exists
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job not found"));

        // Rule 3: Job status must be OPEN
        if (job.getJobStatus() != JobStatus.OPEN) {
            throw new RuntimeException(
                    "Job is not open for applications");
        }

        // Rule 4: Recruiter must be VERIFIED
        if (job.getRecruiter()
                .getVerificationStatus()
                != VerificationStatus.VERIFIED) {

            throw new RuntimeException(
                    "Recruiter is not verified");
        }

        // Rule 5: Application deadline should not expire
        if (job.getApplicationDeadline() != null
                && job.getApplicationDeadline()
                .isBefore(LocalDate.now())) {

            throw new RuntimeException(
                    "Application deadline has expired");
        }

        // Rule 6: Student should not have applied already
        if (applicationRepository
                .existsByStudentIdAndJobId(
                        studentId,
                        jobId)) {

            throw new RuntimeException(
                    "You have already applied for this job");
        }

        // Rule 7: Student profile must be complete
        if (!profileCompletionService
                .isEligibleToApply(student)) {

            throw new RuntimeException(
                    "Your profile is incomplete. Please complete your profile before applying");
        }

        // Rule 8: CGPA eligibility
        if (job.getMinimumCgpa() != null
                && student.getCgpa() != null
                && student.getCgpa()
                .doubleValue() < job.getMinimumCgpa()) {

            throw new RuntimeException(
                    "Your CGPA does not meet the minimum requirement for this job");
        }

        // Rule 9: Branch eligibility
        if (job.getEligibleBranches() != null
                && !job.getEligibleBranches().isEmpty()
                && student.getBranch() != null
                && job.getEligibleBranches()
                .stream()
                .noneMatch(branch ->
                        branch.getId()
                                .equals(
                                        student.getBranch()
                                                .getId()))) {

            throw new RuntimeException(
                    "Your branch is not eligible for this job");
        }

        // Rule 10: Semester eligibility
        if (job.getEligibleSemesters() != null
                && !job.getEligibleSemesters().isEmpty()
                && student.getSemester() != null
                && !job.getEligibleSemesters()
                .contains(student.getSemester())) {

            throw new RuntimeException(
                    "Your semester is not eligible for this job");
        }

        // Create application
        Application application =
                Application.builder()
                        .student(student)
                        .job(job)
                        .applicationStatus(
                                ApplicationStatus.APPLIED)
                        .build();

        Application savedApplication =
                applicationRepository
                        .save(application);

        // Response
        return ApplyJobResponse.builder()
                .applicationId(
                        savedApplication.getId())
                .studentId(
                        student.getId())
                .jobId(
                        job.getId())
                .jobTitle(
                        job.getJobTitle())
                .companyName(
                        job.getRecruiter()
                                .getCompanyName())
                .applicationStatus(
                        savedApplication
                                .getApplicationStatus()
                                .name())
                .appliedAt(
                        savedApplication
                                .getAppliedAt())
                .build();
    }
}