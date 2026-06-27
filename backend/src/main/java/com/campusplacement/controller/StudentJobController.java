package com.campusplacement.controller;

import com.campusplacement.dto.response.JobDiscoveryResponse;
import com.campusplacement.service.StudentJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/jobs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentJobController {

    private final StudentJobService studentJobService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<JobDiscoveryResponse>>
    getAvailableJobs(
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                studentJobService
                        .getAvailableJobs(studentId)
        );
    }
}