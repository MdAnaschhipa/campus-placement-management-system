package com.campusplacement.controller;

import com.campusplacement.dto.request.ApplicationRequest;
import com.campusplacement.dto.response.ApplicationResponse;
import com.campusplacement.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/applications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> applyForJob(
            @Valid @RequestBody ApplicationRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationService.applyForJob(request));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getMyApplications() {
        return ResponseEntity.ok(
                applicationService.getMyApplications()
        );
    }
}