package com.campusplacement.controller;

import com.campusplacement.dto.response.JobModerationResponse;
import com.campusplacement.service.AdminJobModerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/jobs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminJobModerationController {

    private final AdminJobModerationService adminJobModerationService;

    @GetMapping
    public ResponseEntity<List<JobModerationResponse>> getAllJobs() {
        List<JobModerationResponse> response = adminJobModerationService.getAllJobs();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobModerationResponse> getJobById(@PathVariable Long jobId) {
        JobModerationResponse response = adminJobModerationService.getJobById(jobId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{jobId}/close")
    public ResponseEntity<String> closeJob(@PathVariable Long jobId) {
        adminJobModerationService.closeJob(jobId);
        return ResponseEntity.ok("Job closed successfully");
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId) {
        adminJobModerationService.deleteJob(jobId);
        return ResponseEntity.ok("Job deleted successfully");
    }
}