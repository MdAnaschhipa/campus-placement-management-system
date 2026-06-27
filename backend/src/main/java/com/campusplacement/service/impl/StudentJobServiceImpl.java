package com.campusplacement.service.impl;

import com.campusplacement.dto.response.JobDiscoveryResponse;
import com.campusplacement.entity.Job;
import com.campusplacement.entity.Student;
import com.campusplacement.enums.JobStatus;
import com.campusplacement.enums.VerificationStatus;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.service.StudentJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentJobServiceImpl implements StudentJobService {

    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public List<JobDiscoveryResponse> getAvailableJobs(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Set<Long> appliedJobIds =
                applicationRepository
                        .findByStudentIdOrderByAppliedAtDesc(studentId)
                        .stream()
                        .map(application ->
                                application.getJob().getId())
                        .collect(Collectors.toSet());

        return jobRepository.findByJobStatus(JobStatus.OPEN)
                .stream()
                .filter(job ->
                        job.getRecruiter()
                                .getVerificationStatus()
                                == VerificationStatus.VERIFIED)
                .filter(job ->
                        job.getApplicationDeadline() == null
                                || !job.getApplicationDeadline()
                                .isBefore(LocalDate.now()))
                .filter(job ->
                        !appliedJobIds.contains(job.getId()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private JobDiscoveryResponse mapToResponse(Job job) {

        return JobDiscoveryResponse.builder()
                .jobId(job.getId())
                .jobTitle(job.getJobTitle())
                .companyName(
                        job.getRecruiter()
                                .getCompanyName())
                .location(job.getLocation())
                .jobType(
                        job.getJobType() != null
                                ? job.getJobType().name()
                                : null)
                .salaryPackage(job.getSalaryPackage())
                .minimumCgpa(job.getMinimumCgpa())
                .applicationDeadline(
                        job.getApplicationDeadline())
                .numberOfRounds(
                        job.getNumberOfRounds())
                .build();
    }
}