package com.campusplacement.repository;

import com.campusplacement.entity.Announcement;
import com.campusplacement.enums.AnnouncementAudience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByIsActiveTrue();

    List<Announcement> findByTargetAudience(
            AnnouncementAudience targetAudience
    );

    List<Announcement> findByTargetAudienceAndIsActiveTrue(
            AnnouncementAudience targetAudience
    );
}