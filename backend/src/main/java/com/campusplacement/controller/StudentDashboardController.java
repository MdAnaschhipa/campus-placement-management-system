package com.campusplacement.controller;

import com.campusplacement.dto.response.StudentDashboardResponse;
import com.campusplacement.service.StudentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentDashboardController {

    private final StudentDashboardService studentDashboardService;

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDashboardResponse> getDashboard(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentDashboardService.getDashboard(studentId));
    }
}