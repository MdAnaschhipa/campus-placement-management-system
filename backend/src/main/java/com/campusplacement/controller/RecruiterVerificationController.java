package com.campusplacement.controller;

import com.campusplacement.dto.request.RecruiterVerificationRequest;
import com.campusplacement.dto.response.RecruiterVerificationResponse;
import com.campusplacement.service.RecruiterVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter/verification")
@RequiredArgsConstructor
@PreAuthorize("hasRole('RECRUITER')")
public class RecruiterVerificationController {

    private final RecruiterVerificationService recruiterVerificationService;

    @PostMapping
    public ResponseEntity<RecruiterVerificationResponse> submitVerification(
            @Valid @RequestBody RecruiterVerificationRequest request
    ) {
        return ResponseEntity.ok(
                recruiterVerificationService.submitVerification(request)
        );
    }

    @GetMapping
    public ResponseEntity<RecruiterVerificationResponse> getVerificationStatus() {
        return ResponseEntity.ok(
                recruiterVerificationService.getVerificationStatus()
        );
    }
}