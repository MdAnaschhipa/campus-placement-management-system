package com.campusplacement.controller;

import com.campusplacement.dto.request.JobRequest;
import com.campusplacement.dto.response.JobResponse;
import com.campusplacement.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('RECRUITER')")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @Valid @RequestBody JobRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobService.createJob(request));
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long jobId,
            @Valid @RequestBody JobRequest request
    ) {
        return ResponseEntity.ok(
                jobService.updateJob(jobId, request)
        );
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long jobId
    ) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable Long jobId
    ) {
        return ResponseEntity.ok(
                jobService.getJobById(jobId)
        );
    }

    @GetMapping("/my-jobs")
    public ResponseEntity<List<JobResponse>> getRecruiterJobs() {
        return ResponseEntity.ok(
                jobService.getRecruiterJobs()
        );
    }

    @PatchMapping("/{jobId}/close")
    public ResponseEntity<JobResponse> closeJob(
            @PathVariable Long jobId
    ) {
        return ResponseEntity.ok(
                jobService.closeJob(jobId)
        );
    }
}