package com.campusplacement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectionRoundResponse {

    private Long id;
    private Long jobId;
    private Integer roundNumber;
    private String roundName;
}