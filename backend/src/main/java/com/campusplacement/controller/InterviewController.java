package com.campusplacement.controller;

import com.campusplacement.dto.request.InterviewRequest;
import com.campusplacement.dto.response.InterviewResponse;
import com.campusplacement.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruiter/interviews")
@PreAuthorize("hasRole('RECRUITER')")
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping
    public ResponseEntity<InterviewResponse> scheduleInterview(
            @Valid @RequestBody InterviewRequest request) {

        return ResponseEntity.ok(
                interviewService.scheduleInterview(request)
        );
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<InterviewResponse>> getApplicationInterviews(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                interviewService.getApplicationInterviews(applicationId)
        );
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getRecruiterInterviews() {

        return ResponseEntity.ok(
                interviewService.getRecruiterInterviews()
        );
    }
}