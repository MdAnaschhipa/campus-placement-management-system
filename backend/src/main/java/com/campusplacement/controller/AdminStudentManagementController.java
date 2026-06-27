package com.campusplacement.controller;

import com.campusplacement.dto.response.StudentManagementResponse;
import com.campusplacement.service.AdminStudentManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminStudentManagementController {

    private final AdminStudentManagementService adminStudentManagementService;

    @GetMapping
    public ResponseEntity<List<StudentManagementResponse>> getAllStudents() {
        return ResponseEntity.ok(adminStudentManagementService.getAllStudents());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentManagementResponse> getStudentById(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(adminStudentManagementService.getStudentById(studentId));
    }

    @PutMapping("/{studentId}/activate")
    public ResponseEntity<String> activateStudent(@PathVariable Long studentId) {
        adminStudentManagementService.activateStudent(studentId);
        return ResponseEntity.ok("Student activated successfully");
    }

    @PutMapping("/{studentId}/deactivate")
    public ResponseEntity<String> deactivateStudent(@PathVariable Long studentId) {
        adminStudentManagementService.deactivateStudent(studentId);
        return ResponseEntity.ok("Student deactivated successfully");
    }
}