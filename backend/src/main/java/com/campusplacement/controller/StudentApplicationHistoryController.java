package com.campusplacement.controller;

import com.campusplacement.dto.response.ApplicationHistoryResponse;
import com.campusplacement.service.StudentApplicationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/application-history")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentApplicationHistoryController {

    private final StudentApplicationHistoryService
            studentApplicationHistoryService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<ApplicationHistoryResponse>>
    getApplicationHistory(
            @PathVariable Long studentId
    ) {

        List<ApplicationHistoryResponse> response =
                studentApplicationHistoryService
                        .getApplicationHistory(studentId);

        return ResponseEntity.ok(response);
    }
}