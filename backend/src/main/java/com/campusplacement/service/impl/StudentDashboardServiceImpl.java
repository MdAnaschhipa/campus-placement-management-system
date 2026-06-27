package com.campusplacement.service.impl;

import com.campusplacement.dto.response.StudentDashboardResponse;
import com.campusplacement.entity.Student;
import com.campusplacement.enums.ApplicationStatus;
import com.campusplacement.enums.JobStatus;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.service.ProfileCompletionService;
import com.campusplacement.service.StudentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentDashboardServiceImpl implements StudentDashboardService {

    private final StudentRepository studentRepository;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final ProfileCompletionService profileCompletionService;

    @Override
    public StudentDashboardResponse getDashboard(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Integer profileCompletionPercentage = profileCompletionService.calculateCompletion(student);
        Boolean eligibleToApply = profileCompletionService.isEligibleToApply(student);

        Long totalApplications = applicationRepository.countByStudentId(studentId);
        Long selectedApplications = applicationRepository.countByStudentIdAndApplicationStatus(
                studentId, ApplicationStatus.SELECTED);
        Long rejectedApplications = applicationRepository.countByStudentIdAndApplicationStatus(
                studentId, ApplicationStatus.REJECTED);
        Long availableJobs = jobRepository.countByJobStatus(JobStatus.OPEN);

        return StudentDashboardResponse.builder()
                .studentName(student.getUser().getFullName())
                .profileCompletionPercentage(profileCompletionPercentage)
                .eligibleToApply(eligibleToApply)
                .totalApplications(totalApplications)
                .selectedApplications(selectedApplications)
                .rejectedApplications(rejectedApplications)
                .availableJobs(availableJobs)
                .build();
    }
}