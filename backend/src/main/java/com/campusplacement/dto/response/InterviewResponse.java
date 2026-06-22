package com.campusplacement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewResponse {

    private Long id;

    private Long applicationId;

    private Long selectionRoundId;

    private String roundName;

    private LocalDate interviewDate;

    private LocalTime interviewTime;

    private String interviewMode;

    private String meetingLink;

    private String venue;

    private String notes;
}