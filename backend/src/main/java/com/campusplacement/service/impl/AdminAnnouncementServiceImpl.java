package com.campusplacement.service.impl;

import com.campusplacement.dto.request.AnnouncementRequest;
import com.campusplacement.dto.response.AnnouncementResponse;
import com.campusplacement.entity.Announcement;
import com.campusplacement.entity.User;
import com.campusplacement.enums.AuditAction;
import com.campusplacement.enums.AuditEntityType;
import com.campusplacement.repository.AnnouncementRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.AdminAnnouncementService;
import com.campusplacement.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminAnnouncementServiceImpl
        implements AdminAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @Override
    public AnnouncementResponse createAnnouncement(
            AnnouncementRequest request
    ) {

        Announcement announcement = Announcement.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .targetAudience(request.getTargetAudience())
                .isActive(
                        request.getIsActive() != null
                                ? request.getIsActive()
                                : true
                )
                .build();

        Announcement savedAnnouncement =
                announcementRepository.save(announcement);

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String adminEmail = authentication.getName();

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        auditLogService.createLog(
                admin.getId(),
                admin.getEmail(),
                AuditAction.CREATE_ANNOUNCEMENT,
                AuditEntityType.ANNOUNCEMENT,
                savedAnnouncement.getId(),
                "Created announcement: "
                        + savedAnnouncement.getTitle()
        );

        return mapToResponse(savedAnnouncement);
    }

    @Override
    public AnnouncementResponse updateAnnouncement(
            Long announcementId,
            AnnouncementRequest request
    ) {

        Announcement announcement =
                announcementRepository.findById(announcementId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Announcement not found"));

        if (request.getTitle() != null) {
            announcement.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            announcement.setDescription(
                    request.getDescription());
        }

        if (request.getTargetAudience() != null) {
            announcement.setTargetAudience(
                    request.getTargetAudience());
        }

        if (request.getIsActive() != null) {
            announcement.setIsActive(
                    request.getIsActive());
        }

        Announcement updatedAnnouncement =
                announcementRepository.save(announcement);

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String adminEmail = authentication.getName();

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        auditLogService.createLog(
                admin.getId(),
                admin.getEmail(),
                AuditAction.UPDATE_ANNOUNCEMENT,
                AuditEntityType.ANNOUNCEMENT,
                updatedAnnouncement.getId(),
                "Updated announcement: "
                        + updatedAnnouncement.getTitle()
        );

        return mapToResponse(updatedAnnouncement);
    }

    @Override
    public void deleteAnnouncement(Long announcementId) {

        Announcement announcement =
                announcementRepository.findById(announcementId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Announcement not found"));

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String adminEmail = authentication.getName();

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        auditLogService.createLog(
                admin.getId(),
                admin.getEmail(),
                AuditAction.DELETE_ANNOUNCEMENT,
                AuditEntityType.ANNOUNCEMENT,
                announcement.getId(),
                "Deleted announcement: "
                        + announcement.getTitle()
        );

        announcementRepository.delete(announcement);
    }

    @Override
    @Transactional(readOnly = true)
    public AnnouncementResponse getAnnouncementById(
            Long announcementId
    ) {

        Announcement announcement =
                announcementRepository.findById(announcementId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Announcement not found"));

        return mapToResponse(announcement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementResponse> getAllAnnouncements() {

        return announcementRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AnnouncementResponse mapToResponse(
            Announcement announcement
    ) {

        return AnnouncementResponse.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .description(
                        announcement.getDescription())
                .targetAudience(
                        announcement.getTargetAudience() != null
                                ? announcement
                                .getTargetAudience()
                                .name()
                                : null
                )
                .isActive(announcement.getIsActive())
                .createdAt(announcement.getCreatedAt())
                .updatedAt(announcement.getUpdatedAt())
                .build();
    }
}