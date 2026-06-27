package com.campusplacement.dto.request;

import com.campusplacement.enums.AnnouncementAudience;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementRequest {

    private String title;
    private String description;
    private AnnouncementAudience targetAudience;
    private Boolean isActive;
}