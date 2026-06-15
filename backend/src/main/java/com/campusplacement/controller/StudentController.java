package com.campusplacement.controller;

import com.campusplacement.dto.request.StudentProfileRequest;
import com.campusplacement.dto.response.StudentProfileResponse;
import com.campusplacement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/profile")
    public ResponseEntity<StudentProfileResponse> getProfile() {
        return ResponseEntity.ok(studentService.getProfile());
    }

    @PostMapping("/profile")
    public ResponseEntity<StudentProfileResponse> createProfile(
            @RequestBody StudentProfileRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createProfile(request));
    }

    @PutMapping("/profile")
    public ResponseEntity<StudentProfileResponse> updateProfile(
            @RequestBody StudentProfileRequest request) {

        return ResponseEntity.ok(
                studentService.updateProfile(request)
        );
    }
}