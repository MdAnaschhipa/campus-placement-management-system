package com.campusplacement.repository;

import com.campusplacement.entity.CompanySize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanySizeRepository extends JpaRepository<CompanySize, Long> {

    Optional<CompanySize> findBySizeName(String sizeName);

    boolean existsBySizeName(String sizeName);

    List<CompanySize> findByIsActiveTrueOrderBySizeNameAsc();
}