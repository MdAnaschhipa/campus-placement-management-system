package com.campusplacement.dto.request;

import com.campusplacement.enums.RoundStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRoundRequest {

    private Long applicationId;
    private Long selectionRoundId;
    private RoundStatus roundStatus;
}