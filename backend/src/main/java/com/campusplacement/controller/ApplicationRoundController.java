package com.campusplacement.controller;

import com.campusplacement.dto.request.ApplicationRoundRequest;
import com.campusplacement.dto.response.ApplicationRoundResponse;
import com.campusplacement.service.ApplicationRoundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruiter/application-rounds")
@PreAuthorize("hasRole('RECRUITER')")
public class ApplicationRoundController {

    private final ApplicationRoundService applicationRoundService;

    @PutMapping
    public ResponseEntity<ApplicationRoundResponse> updateRoundStatus(
            @Valid @RequestBody ApplicationRoundRequest request) {

        return ResponseEntity.ok(
                applicationRoundService.updateRoundStatus(request)
        );
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<ApplicationRoundResponse>> getApplicationRounds(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                applicationRoundService.getApplicationRounds(applicationId)
        );
    }
}