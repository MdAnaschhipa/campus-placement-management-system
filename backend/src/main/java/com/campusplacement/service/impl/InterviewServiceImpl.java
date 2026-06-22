package com.campusplacement.service.impl;

import com.campusplacement.dto.request.InterviewRequest;
import com.campusplacement.dto.response.InterviewResponse;
import com.campusplacement.entity.Application;
import com.campusplacement.entity.Interview;
import com.campusplacement.entity.Recruiter;
import com.campusplacement.entity.SelectionRound;
import com.campusplacement.entity.User;
import com.campusplacement.enums.Role;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.InterviewRepository;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.SelectionRoundRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final SelectionRoundRepository selectionRoundRepository;
    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    @Override
    public InterviewResponse scheduleInterview(InterviewRequest request) {

        User currentUser = getCurrentUser();
        validateRecruiterRole(currentUser);

        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with id: "
                                        + request.getApplicationId()
                        )
                );

        SelectionRound selectionRound = selectionRoundRepository.findById(
                        request.getSelectionRoundId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Selection round not found with id: "
                                        + request.getSelectionRoundId()
                        )
                );

        Recruiter recruiter = recruiterRepository.findByUserId(currentUser.getId())
                .orElseThrow(() ->
                        new RuntimeException("Recruiter profile not found")
                );

        if (!application.getJob().getRecruiter().getId().equals(recruiter.getId())) {
            throw new RuntimeException(
                    "You are not authorized to schedule interviews for this application"
            );
        }

        if (!selectionRound.getJob().getId().equals(application.getJob().getId())) {
            throw new RuntimeException(
                    "Selection round does not belong to this application's job"
            );
        }

        if (interviewRepository.existsByApplicationIdAndSelectionRoundId(
                request.getApplicationId(),
                request.getSelectionRoundId()
        )) {
            throw new RuntimeException(
                    "Interview already scheduled for this application and round"
            );
        }

        Interview interview = Interview.builder()
                .application(application)
                .selectionRound(selectionRound)
                .interviewDate(request.getInterviewDate())
                .interviewTime(request.getInterviewTime())
                .interviewMode(request.getInterviewMode())
                .meetingLink(request.getMeetingLink())
                .venue(request.getVenue())
                .notes(request.getNotes())
                .build();

        Interview savedInterview = interviewRepository.save(interview);

        return mapToResponse(savedInterview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewResponse> getApplicationInterviews(Long applicationId) {

        applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with id: "
                                        + applicationId
                        )
                );

        return interviewRepository.findByApplicationId(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewResponse> getRecruiterInterviews() {

        User currentUser = getCurrentUser();
        validateRecruiterRole(currentUser);

        Recruiter recruiter = recruiterRepository.findByUserId(currentUser.getId())
                .orElseThrow(() ->
                        new RuntimeException("Recruiter profile not found")
                );

        return interviewRepository.findByApplicationJobRecruiterId(recruiter.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void validateRecruiterRole(User user) {

        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException(
                    "Only recruiters can perform this action"
            );
        }
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException(
                    "No authenticated user found in security context."
            );
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found: " + email
                        )
                );
    }

    private InterviewResponse mapToResponse(Interview interview) {

        return InterviewResponse.builder()
                .id(interview.getId())
                .applicationId(interview.getApplication().getId())
                .selectionRoundId(interview.getSelectionRound().getId())
                .roundName(interview.getSelectionRound().getRoundName())
                .interviewDate(interview.getInterviewDate())
                .interviewTime(interview.getInterviewTime())
                .interviewMode(
                        interview.getInterviewMode() != null
                                ? interview.getInterviewMode().name()
                                : null
                )
                .meetingLink(interview.getMeetingLink())
                .venue(interview.getVenue())
                .notes(interview.getNotes())
                .build();
    }
}