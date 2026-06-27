package com.campusplacement.controller;

import com.campusplacement.dto.response.RecruiterManagementResponse;
import com.campusplacement.service.AdminRecruiterManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/recruiters/manage")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminRecruiterManagementController {

    private final AdminRecruiterManagementService adminRecruiterManagementService;

    @GetMapping
    public ResponseEntity<List<RecruiterManagementResponse>> getAllRecruiters() {
        return ResponseEntity.ok(adminRecruiterManagementService.getAllRecruiters());
    }

    @GetMapping("/{recruiterId}")
    public ResponseEntity<RecruiterManagementResponse> getRecruiterById(
            @PathVariable Long recruiterId) {
        return ResponseEntity.ok(adminRecruiterManagementService.getRecruiterById(recruiterId));
    }

    @PutMapping("/{recruiterId}/activate")
    public ResponseEntity<String> activateRecruiter(@PathVariable Long recruiterId) {
        adminRecruiterManagementService.activateRecruiter(recruiterId);
        return ResponseEntity.ok("Recruiter activated successfully");
    }

    @PutMapping("/{recruiterId}/deactivate")
    public ResponseEntity<String> deactivateRecruiter(@PathVariable Long recruiterId) {
        adminRecruiterManagementService.deactivateRecruiter(recruiterId);
        return ResponseEntity.ok("Recruiter deactivated successfully");
    }
}