package com.campusplacement.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectionRoundRequest {

    private Long jobId;
    private Integer roundNumber;
    private String roundName;
}