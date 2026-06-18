package com.campusplacement.repository;

import com.campusplacement.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByBranchCode(String branchCode);

    Optional<Branch> findByBranchName(String branchName);

    boolean existsByBranchCode(String branchCode);

    boolean existsByBranchName(String branchName);

    List<Branch> findByIsActiveTrueOrderByBranchNameAsc();
}