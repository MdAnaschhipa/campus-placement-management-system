package com.campusplacement.controller;

import com.campusplacement.dto.request.RecruiterProfileRequest;
import com.campusplacement.dto.response.RecruiterProfileResponse;
import com.campusplacement.service.RecruiterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter/profile")
@RequiredArgsConstructor
@PreAuthorize("hasRole('RECRUITER')")
public class RecruiterController {

    private final RecruiterService recruiterService;

    @GetMapping
    public ResponseEntity<RecruiterProfileResponse> getProfile() {
        return ResponseEntity.ok(
                recruiterService.getProfile()
        );
    }

    @PostMapping
    public ResponseEntity<RecruiterProfileResponse> createProfile(
            @Valid @RequestBody RecruiterProfileRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        recruiterService.createProfile(request)
                );
    }

    @PutMapping
    public ResponseEntity<RecruiterProfileResponse> updateProfile(
            @Valid @RequestBody RecruiterProfileRequest request
    ) {
        return ResponseEntity.ok(
                recruiterService.updateProfile(request)
        );
    }
}