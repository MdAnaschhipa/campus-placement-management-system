package com.campusplacement.controller;

import com.campusplacement.dto.response.RecruiterDashboardResponse;
import com.campusplacement.service.RecruiterDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruiter/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('RECRUITER')")
public class RecruiterDashboardController {

    private final RecruiterDashboardService recruiterDashboardService;

    @GetMapping
    public ResponseEntity<RecruiterDashboardResponse> getDashboard() {
        return ResponseEntity.ok(
                recruiterDashboardService.getDashboard()
        );
    }
}