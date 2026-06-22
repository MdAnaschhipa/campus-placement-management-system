package com.campusplacement.repository;

import com.campusplacement.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    long countByApplicationJobRecruiterId(
            Long recruiterId
    );

    List<Interview> findByApplicationId(
            Long applicationId
    );

    Optional<Interview> findByApplicationIdAndSelectionRoundId(
            Long applicationId,
            Long selectionRoundId
    );

    List<Interview> findByApplicationJobRecruiterId(
            Long recruiterId
    );

    boolean existsByApplicationIdAndSelectionRoundId(
            Long applicationId,
            Long selectionRoundId
    );
}