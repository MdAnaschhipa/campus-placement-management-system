package com.campusplacement.service.impl;

import com.campusplacement.dto.request.RecruiterVerificationRequest;
import com.campusplacement.dto.response.RecruiterVerificationResponse;
import com.campusplacement.entity.Recruiter;
import com.campusplacement.entity.User;
import com.campusplacement.enums.Role;
import com.campusplacement.enums.VerificationStatus;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.RecruiterVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruiterVerificationServiceImpl implements RecruiterVerificationService {

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    @Override
    public RecruiterVerificationResponse submitVerification(
            RecruiterVerificationRequest request
    ) {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Recruiter recruiter = recruiterRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Recruiter profile not found")
                );

        if (request.getOfficialCompanyEmail() != null
                && !request.getOfficialCompanyEmail().equals(
                recruiter.getOfficialCompanyEmail())
                && recruiterRepository.existsByOfficialCompanyEmail(
                request.getOfficialCompanyEmail())) {

            throw new RuntimeException(
                    "Official company email already exists"
            );
        }

        if (request.getGstNumber() != null
                && !request.getGstNumber().equals(
                recruiter.getGstNumber())
                && recruiterRepository.existsByGstNumber(
                request.getGstNumber())) {

            throw new RuntimeException(
                    "GST number already exists"
            );
        }

        recruiter.setOfficialCompanyEmail(
                request.getOfficialCompanyEmail()
        );

        recruiter.setGstNumber(
                request.getGstNumber()
        );

        recruiter.setRegistrationCertificateUrl(
                request.getRegistrationCertificateUrl()
        );

        recruiter.setVerificationStatus(
                VerificationStatus.PENDING
        );

        Recruiter updatedRecruiter =
                recruiterRepository.save(recruiter);

        return mapToResponse(updatedRecruiter);
    }

    @Override
    @Transactional(readOnly = true)
    public RecruiterVerificationResponse getVerificationStatus() {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Recruiter recruiter = recruiterRepository.findByUserId(
                        user.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found"
                        )
                );

        return mapToResponse(recruiter);
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

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "No authenticated user found in security context."
            );
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found: "
                                        + email
                        )
                );
    }

    private RecruiterVerificationResponse mapToResponse(
            Recruiter recruiter
    ) {

        return RecruiterVerificationResponse.builder()
                .recruiterId(recruiter.getId())
                .companyName(recruiter.getCompanyName())
                .officialCompanyEmail(
                        recruiter.getOfficialCompanyEmail()
                )
                .gstNumber(recruiter.getGstNumber())
                .registrationCertificateUrl(
                        recruiter.getRegistrationCertificateUrl()
                )
                .verificationStatus(
                        recruiter.getVerificationStatus() != null
                                ? recruiter.getVerificationStatus()
                                .getDisplayName()
                                : null
                )
                .updatedAt(recruiter.getUpdatedAt())
                .build();
    }
}