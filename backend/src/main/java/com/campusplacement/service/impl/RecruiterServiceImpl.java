package com.campusplacement.service.impl;

import com.campusplacement.dto.request.RecruiterProfileRequest;
import com.campusplacement.dto.response.RecruiterProfileResponse;
import com.campusplacement.entity.CompanySize;
import com.campusplacement.entity.Industry;
import com.campusplacement.entity.Recruiter;
import com.campusplacement.enums.Role;
import com.campusplacement.entity.User;
import com.campusplacement.repository.CompanySizeRepository;
import com.campusplacement.repository.IndustryRepository;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.RecruiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;
    private final IndustryRepository industryRepository;
    private final CompanySizeRepository companySizeRepository;

    @Override
    @Transactional(readOnly = true)
    public RecruiterProfileResponse getProfile() {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Recruiter recruiter = recruiterRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        return mapToResponse(recruiter);
    }

    @Override
    public RecruiterProfileResponse createProfile(RecruiterProfileRequest request) {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        if (recruiterRepository.existsByUserId(user.getId())) {
            throw new RuntimeException("Profile already exists");
        }

        Industry industry = resolveIndustry(request.getIndustryId());
        CompanySize companySize = resolveCompanySize(request.getCompanySizeId());

        if (request.getOfficialCompanyEmail() != null
                && recruiterRepository.existsByOfficialCompanyEmail(request.getOfficialCompanyEmail())) {
            throw new RuntimeException("Official company email already exists");
        }

        if (request.getGstNumber() != null
                && recruiterRepository.existsByGstNumber(request.getGstNumber())) {
            throw new RuntimeException("GST number already exists");
        }

        Recruiter recruiter = Recruiter.builder()
                .user(user)
                .companyName(request.getCompanyName())
                .industry(industry)
                .companySize(companySize)
                .companyWebsite(request.getCompanyWebsite())
                .companyDescription(request.getCompanyDescription())
                .headquarters(request.getHeadquarters())
                .companyLogoUrl(request.getCompanyLogoUrl())
                .contactPersonName(request.getContactPersonName())
                .contactPersonDesignation(request.getContactPersonDesignation())
                .contactPhoneNumber(request.getContactPhoneNumber())
                .officialCompanyEmail(request.getOfficialCompanyEmail())
                .gstNumber(request.getGstNumber())
                .registrationCertificateUrl(request.getRegistrationCertificateUrl())
                .build();

        recruiter.setIsProfileCompleted(isProfileComplete(recruiter));

        Recruiter savedRecruiter = recruiterRepository.save(recruiter);

        return mapToResponse(savedRecruiter);
    }

    @Override
    public RecruiterProfileResponse updateProfile(RecruiterProfileRequest request) {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Recruiter recruiter = recruiterRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        if (request.getOfficialCompanyEmail() != null
                && !request.getOfficialCompanyEmail().equals(recruiter.getOfficialCompanyEmail())
                && recruiterRepository.existsByOfficialCompanyEmail(request.getOfficialCompanyEmail())) {
            throw new RuntimeException("Official company email already exists");
        }

        if (request.getGstNumber() != null
                && !request.getGstNumber().equals(recruiter.getGstNumber())
                && recruiterRepository.existsByGstNumber(request.getGstNumber())) {
            throw new RuntimeException("GST number already exists");
        }

        if (request.getCompanyName() != null) {
            recruiter.setCompanyName(request.getCompanyName());
        }

        if (request.getIndustryId() != null) {
            recruiter.setIndustry(resolveIndustry(request.getIndustryId()));
        }

        if (request.getCompanySizeId() != null) {
            recruiter.setCompanySize(resolveCompanySize(request.getCompanySizeId()));
        }

        if (request.getCompanyWebsite() != null) {
            recruiter.setCompanyWebsite(request.getCompanyWebsite());
        }

        if (request.getCompanyDescription() != null) {
            recruiter.setCompanyDescription(request.getCompanyDescription());
        }

        if (request.getHeadquarters() != null) {
            recruiter.setHeadquarters(request.getHeadquarters());
        }

        if (request.getCompanyLogoUrl() != null) {
            recruiter.setCompanyLogoUrl(request.getCompanyLogoUrl());
        }

        if (request.getContactPersonName() != null) {
            recruiter.setContactPersonName(request.getContactPersonName());
        }

        if (request.getContactPersonDesignation() != null) {
            recruiter.setContactPersonDesignation(request.getContactPersonDesignation());
        }

        if (request.getContactPhoneNumber() != null) {
            recruiter.setContactPhoneNumber(request.getContactPhoneNumber());
        }

        if (request.getOfficialCompanyEmail() != null) {
            recruiter.setOfficialCompanyEmail(request.getOfficialCompanyEmail());
        }

        if (request.getGstNumber() != null) {
            recruiter.setGstNumber(request.getGstNumber());
        }

        if (request.getRegistrationCertificateUrl() != null) {
            recruiter.setRegistrationCertificateUrl(request.getRegistrationCertificateUrl());
        }

        recruiter.setIsProfileCompleted(isProfileComplete(recruiter));

        Recruiter updatedRecruiter = recruiterRepository.save(recruiter);

        return mapToResponse(updatedRecruiter);
    }

    private void validateRecruiterRole(User user) {
        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException("Only recruiters can perform this action");
        }
    }

    private boolean isProfileComplete(Recruiter recruiter) {
        return isPresent(recruiter.getCompanyName())
                && recruiter.getIndustry() != null
                && recruiter.getCompanySize() != null
                && isPresent(recruiter.getOfficialCompanyEmail())
                && isPresent(recruiter.getGstNumber())
                && isPresent(recruiter.getRegistrationCertificateUrl())
                && isPresent(recruiter.getContactPersonName())
                && isPresent(recruiter.getContactPhoneNumber());
    }

    private boolean isPresent(String value) {
        return value != null && !value.isBlank();
    }

    private Industry resolveIndustry(Long industryId) {
        if (industryId == null) {
            return null;
        }

        return industryRepository.findById(industryId)
                .orElseThrow(() -> new RuntimeException("Industry not found with id: " + industryId));
    }

    private CompanySize resolveCompanySize(Long companySizeId) {
        if (companySizeId == null) {
            return null;
        }

        return companySizeRepository.findById(companySizeId)
                .orElseThrow(() -> new RuntimeException("Company size not found with id: " + companySizeId));
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

    private RecruiterProfileResponse mapToResponse(Recruiter recruiter) {

        return RecruiterProfileResponse.builder()
                .id(recruiter.getId())
                .companyName(recruiter.getCompanyName())
                .industryId(recruiter.getIndustry() != null ? recruiter.getIndustry().getId() : null)
                .industryName(recruiter.getIndustry() != null ? recruiter.getIndustry().getIndustryName() : null)
                .companySizeId(recruiter.getCompanySize() != null ? recruiter.getCompanySize().getId() : null)
                .companySizeName(recruiter.getCompanySize() != null ? recruiter.getCompanySize().getSizeName() : null)
                .companyWebsite(recruiter.getCompanyWebsite())
                .companyDescription(recruiter.getCompanyDescription())
                .headquarters(recruiter.getHeadquarters())
                .companyLogoUrl(recruiter.getCompanyLogoUrl())
                .contactPersonName(recruiter.getContactPersonName())
                .contactPersonDesignation(recruiter.getContactPersonDesignation())
                .contactPhoneNumber(recruiter.getContactPhoneNumber())
                .officialCompanyEmail(recruiter.getOfficialCompanyEmail())
                .gstNumber(recruiter.getGstNumber())
                .registrationCertificateUrl(recruiter.getRegistrationCertificateUrl())
                .verificationStatus(recruiter.getVerificationStatus() != null ? recruiter.getVerificationStatus().getDisplayName() : null)
                .isProfileCompleted(recruiter.getIsProfileCompleted())
                .createdAt(recruiter.getCreatedAt())
                .updatedAt(recruiter.getUpdatedAt())
                .build();
    }
}