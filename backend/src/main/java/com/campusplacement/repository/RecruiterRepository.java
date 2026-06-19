package com.campusplacement.repository;

import com.campusplacement.entity.Recruiter;
import com.campusplacement.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    Optional<Recruiter> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    List<Recruiter> findByVerificationStatus(
            VerificationStatus verificationStatus
    );

    Optional<Recruiter> findByOfficialCompanyEmail(
            String officialCompanyEmail
    );

    boolean existsByOfficialCompanyEmail(
            String officialCompanyEmail
    );

    boolean existsByGstNumber(
            String gstNumber
    );

    long countByVerificationStatus(
            VerificationStatus verificationStatus
    );
}