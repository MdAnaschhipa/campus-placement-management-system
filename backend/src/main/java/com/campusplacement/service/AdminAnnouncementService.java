package com.campusplacement.service;

import com.campusplacement.dto.request.AnnouncementRequest;
import com.campusplacement.dto.response.AnnouncementResponse;

import java.util.List;

public interface AdminAnnouncementService {

    AnnouncementResponse createAnnouncement(
            AnnouncementRequest request
    );

    AnnouncementResponse updateAnnouncement(
            Long announcementId,
            AnnouncementRequest request
    );

    void deleteAnnouncement(
            Long announcementId
    );

    AnnouncementResponse getAnnouncementById(
            Long announcementId
    );

    List<AnnouncementResponse> getAllAnnouncements();
}