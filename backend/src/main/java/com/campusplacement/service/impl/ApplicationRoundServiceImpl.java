package com.campusplacement.service.impl;

import com.campusplacement.dto.request.ApplicationRoundRequest;
import com.campusplacement.dto.response.ApplicationRoundResponse;
import com.campusplacement.entity.Application;
import com.campusplacement.entity.ApplicationRound;
import com.campusplacement.entity.Recruiter;
import com.campusplacement.enums.Role;
import com.campusplacement.entity.SelectionRound;
import com.campusplacement.entity.User;
import com.campusplacement.repository.ApplicationRepository;
import com.campusplacement.repository.ApplicationRoundRepository;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.SelectionRoundRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.ApplicationRoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationRoundServiceImpl implements ApplicationRoundService {

    private final ApplicationRoundRepository applicationRoundRepository;
    private final ApplicationRepository applicationRepository;
    private final SelectionRoundRepository selectionRoundRepository;
    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    @Override
    public ApplicationRoundResponse updateRoundStatus(ApplicationRoundRequest request) {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + request.getApplicationId()));

        SelectionRound selectionRound = selectionRoundRepository.findById(request.getSelectionRoundId())
                .orElseThrow(() -> new RuntimeException("Selection round not found with id: " + request.getSelectionRoundId()));

        Recruiter recruiter = recruiterRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        if (!application.getJob().getRecruiter().getId().equals(recruiter.getId())) {
            throw new RuntimeException("You do not have permission to modify this application.");
        }

        if (!selectionRound.getJob().getId().equals(application.getJob().getId())) {
            throw new RuntimeException("Selection round does not belong to the same job as this application.");
        }

        ApplicationRound applicationRound = applicationRoundRepository
                .findByApplicationIdAndSelectionRoundId(application.getId(), selectionRound.getId())
                .orElse(null);

        if (applicationRound == null) {
            ApplicationRound.ApplicationRoundBuilder builder = ApplicationRound.builder()
                    .application(application)
                    .selectionRound(selectionRound);

            if (request.getRoundStatus() != null) {
                builder.roundStatus(request.getRoundStatus());
            }

            applicationRound = builder.build();
        } else if (request.getRoundStatus() != null) {
            applicationRound.setRoundStatus(request.getRoundStatus());
        }

        ApplicationRound savedRound = applicationRoundRepository.save(applicationRound);

        return mapToResponse(savedRound);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRoundResponse> getApplicationRounds(Long applicationId) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with id: " + applicationId
                        )
                );

        return applicationRoundRepository
                .findByApplicationIdOrderBySelectionRoundRoundNumberAsc(application.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void validateRecruiterRole(User user) {
        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException("Only recruiters can perform this action");
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

    private ApplicationRoundResponse mapToResponse(ApplicationRound applicationRound) {

        return ApplicationRoundResponse.builder()
                .id(applicationRound.getId())
                .applicationId(applicationRound.getApplication().getId())
                .selectionRoundId(applicationRound.getSelectionRound().getId())
                .roundNumber(applicationRound.getSelectionRound().getRoundNumber())
                .roundName(applicationRound.getSelectionRound().getRoundName())
                .roundStatus(applicationRound.getRoundStatus() != null
                        ? applicationRound.getRoundStatus().getDisplayName()
                        : null)
                .build();
    }
}