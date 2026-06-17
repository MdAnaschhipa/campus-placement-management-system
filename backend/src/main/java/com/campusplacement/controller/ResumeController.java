package com.campusplacement.controller;

import com.campusplacement.dto.response.ResumeResponse;
import com.campusplacement.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/student/resume")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResumeResponse> uploadResume(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                resumeService.uploadResume(file)
        );
    }

    @GetMapping
    public ResponseEntity<ResumeResponse> getMyResume() {

        return ResponseEntity.ok(
                resumeService.getMyResume()
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteResume() {

        resumeService.deleteResume();

        return ResponseEntity.noContent().build();
    }
}