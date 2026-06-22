package com.campusplacement.controller;

import com.campusplacement.dto.request.SelectionRoundRequest;
import com.campusplacement.dto.response.SelectionRoundResponse;
import com.campusplacement.service.SelectionRoundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter/rounds")
@RequiredArgsConstructor
@PreAuthorize("hasRole('RECRUITER')")
public class SelectionRoundController {

    private final SelectionRoundService selectionRoundService;

    @PostMapping
    public ResponseEntity<SelectionRoundResponse> createRound(
            @Valid @RequestBody SelectionRoundRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(selectionRoundService.createRound(request));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<SelectionRoundResponse>> getJobRounds(
            @PathVariable Long jobId
    ) {
        return ResponseEntity.ok(
                selectionRoundService.getJobRounds(jobId)
        );
    }
}