package com.campusplacement.controller;

import com.campusplacement.dto.response.RecruiterVerificationResponse;
import com.campusplacement.service.AdminRecruiterVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/recruiters")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminRecruiterVerificationController {

    private final AdminRecruiterVerificationService adminRecruiterVerificationService;

    @GetMapping("/pending")
    public ResponseEntity<List<RecruiterVerificationResponse>> getPendingRecruiters() {
        return ResponseEntity.ok(adminRecruiterVerificationService.getPendingRecruiters());
    }

    @GetMapping("/{recruiterId}")
    public ResponseEntity<RecruiterVerificationResponse> getRecruiterDetails(
            @PathVariable Long recruiterId) {
        return ResponseEntity.ok(adminRecruiterVerificationService.getRecruiterDetails(recruiterId));
    }

    @PutMapping("/{recruiterId}/approve")
    public ResponseEntity<String> approveRecruiter(@PathVariable Long recruiterId) {
        adminRecruiterVerificationService.approveRecruiter(recruiterId);
        return ResponseEntity.ok("Recruiter approved successfully");
    }

    @PutMapping("/{recruiterId}/reject")
    public ResponseEntity<String> rejectRecruiter(@PathVariable Long recruiterId) {
        adminRecruiterVerificationService.rejectRecruiter(recruiterId);
        return ResponseEntity.ok("Recruiter rejected successfully");
    }
}