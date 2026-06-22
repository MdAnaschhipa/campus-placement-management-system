package com.campusplacement.repository;

import com.campusplacement.entity.ApplicationRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRoundRepository extends JpaRepository<ApplicationRound, Long> {

    List<ApplicationRound>
    findByApplicationIdOrderBySelectionRoundRoundNumberAsc(
            Long applicationId
    );

    Optional<ApplicationRound>
    findByApplicationIdAndSelectionRoundId(
            Long applicationId,
            Long selectionRoundId
    );

    boolean existsByApplicationIdAndSelectionRoundId(
            Long applicationId,
            Long selectionRoundId
    );
}