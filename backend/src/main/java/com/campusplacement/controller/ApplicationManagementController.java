package com.campusplacement.controller;

import com.campusplacement.dto.request.ApplicationStatusRequest;
import com.campusplacement.dto.response.ApplicationStatusResponse;
import com.campusplacement.service.ApplicationManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruiter/applications")
@PreAuthorize("hasRole('RECRUITER')")
public class ApplicationManagementController {

    private final ApplicationManagementService applicationManagementService;

    @PutMapping("/status")
    public ResponseEntity<ApplicationStatusResponse> updateApplicationStatus(
            @Valid @RequestBody ApplicationStatusRequest request) {

        return ResponseEntity.ok(
                applicationManagementService.updateApplicationStatus(request)
        );
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationStatusResponse>> getApplicationsByJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                applicationManagementService.getApplicationsByJob(jobId)
        );
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationStatusResponse> getApplicationById(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                applicationManagementService.getApplicationById(applicationId)
        );
    }
}