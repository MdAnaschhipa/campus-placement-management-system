package com.campusplacement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRoundResponse {

    private Long id;
    private Long applicationId;
    private Long selectionRoundId;
    private Integer roundNumber;
    private String roundName;
    private String roundStatus;
}