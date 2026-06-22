package com.campusplacement.dto.request;

import com.campusplacement.enums.InterviewMode;
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
public class InterviewRequest {

    private Long applicationId;

    private Long selectionRoundId;

    private LocalDate interviewDate;

    private LocalTime interviewTime;

    private InterviewMode interviewMode;

    private String meetingLink;

    private String venue;

    private String notes;
}