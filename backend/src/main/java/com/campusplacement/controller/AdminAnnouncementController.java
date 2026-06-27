package com.campusplacement.controller;

import com.campusplacement.dto.request.AnnouncementRequest;
import com.campusplacement.dto.response.AnnouncementResponse;
import com.campusplacement.service.AdminAnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/announcements")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminAnnouncementController {

    private final AdminAnnouncementService adminAnnouncementService;

    @PostMapping
    public ResponseEntity<AnnouncementResponse> createAnnouncement(
            @RequestBody AnnouncementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminAnnouncementService.createAnnouncement(request));
    }

    @PutMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> updateAnnouncement(
            @PathVariable Long announcementId,
            @RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(adminAnnouncementService.updateAnnouncement(announcementId, request));
    }

    @DeleteMapping("/{announcementId}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long announcementId) {
        adminAnnouncementService.deleteAnnouncement(announcementId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> getAnnouncementById(
            @PathVariable Long announcementId) {
        return ResponseEntity.ok(adminAnnouncementService.getAnnouncementById(announcementId));
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementResponse>> getAllAnnouncements() {
        return ResponseEntity.ok(adminAnnouncementService.getAllAnnouncements());
    }
}