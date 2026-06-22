package com.campusplacement.service.impl;

import com.campusplacement.dto.request.SelectionRoundRequest;
import com.campusplacement.dto.response.SelectionRoundResponse;
import com.campusplacement.entity.Job;
import com.campusplacement.entity.Recruiter;
import com.campusplacement.enums.Role;
import com.campusplacement.entity.SelectionRound;
import com.campusplacement.entity.User;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.SelectionRoundRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.SelectionRoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SelectionRoundServiceImpl implements SelectionRoundService {

    private final SelectionRoundRepository selectionRoundRepository;
    private final JobRepository jobRepository;
    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    @Override
    public SelectionRoundResponse createRound(SelectionRoundRequest request) {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + request.getJobId()));

        Recruiter recruiter = recruiterRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        if (!job.getRecruiter().getId().equals(recruiter.getId())) {
            throw new RuntimeException("You do not have permission to modify this job.");
        }

        if (selectionRoundRepository.existsByJobIdAndRoundNumber(job.getId(), request.getRoundNumber())) {
            throw new RuntimeException("Round number already exists for this job");
        }

        SelectionRound selectionRound = SelectionRound.builder()
                .job(job)
                .roundNumber(request.getRoundNumber())
                .roundName(request.getRoundName())
                .build();

        SelectionRound savedRound = selectionRoundRepository.save(selectionRound);

        return mapToResponse(savedRound);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectionRoundResponse> getJobRounds(Long jobId) {

        return selectionRoundRepository.findByJobIdOrderByRoundNumberAsc(jobId)
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

    private SelectionRoundResponse mapToResponse(SelectionRound selectionRound) {

        return SelectionRoundResponse.builder()
                .id(selectionRound.getId())
                .jobId(selectionRound.getJob().getId())
                .roundNumber(selectionRound.getRoundNumber())
                .roundName(selectionRound.getRoundName())
                .build();
    }
}