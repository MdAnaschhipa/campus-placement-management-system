package com.campusplacement.controller;

import com.campusplacement.dto.response.ApplyJobResponse;
import com.campusplacement.service.StudentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/applications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentApplicationController {

    private final StudentApplicationService studentApplicationService;

    @PostMapping("/{studentId}/apply/{jobId}")
    public ResponseEntity<ApplyJobResponse> applyJob(
            @PathVariable Long studentId,
            @PathVariable Long jobId) {
        ApplyJobResponse response = studentApplicationService.applyJob(studentId, jobId);
        return ResponseEntity.ok(response);
    }
}
