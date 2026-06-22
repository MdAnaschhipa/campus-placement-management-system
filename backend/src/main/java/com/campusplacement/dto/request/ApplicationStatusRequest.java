package com.campusplacement.dto.request;

import com.campusplacement.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationStatusRequest {

    private Long applicationId;

    private ApplicationStatus applicationStatus;
}