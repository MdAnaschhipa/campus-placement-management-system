package com.campusplacement.service.impl;

import com.campusplacement.dto.response.ApplicationHistoryResponse;
import com.campusplacement.entity.Application;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.service.StudentApplicationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentApplicationHistoryServiceImpl
        implements StudentApplicationHistoryService {

    private final StudentRepository studentRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public List<ApplicationHistoryResponse>
    getApplicationHistory(Long studentId) {

        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"));

        return applicationRepository
                .findByStudentIdOrderByAppliedAtDesc(
                        studentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ApplicationHistoryResponse
    mapToResponse(Application application) {

        return ApplicationHistoryResponse.builder()
                .applicationId(application.getId())
                .jobId(application.getJob().getId())
                .jobTitle(
                        application.getJob()
                                .getJobTitle())
                .companyName(
                        application.getJob()
                                .getRecruiter()
                                .getCompanyName())
                .applicationStatus(
                        application
                                .getApplicationStatus()
                                .name())
                .appliedAt(
                        application
                                .getAppliedAt())
                .build();
    }
}