package com.campusplacement.repository;

import com.campusplacement.entity.SelectionRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectionRoundRepository extends JpaRepository<SelectionRound, Long> {

    List<SelectionRound> findByJobIdOrderByRoundNumberAsc(
            Long jobId
    );

    boolean existsByJobIdAndRoundNumber(
            Long jobId,
            Integer roundNumber
    );
}