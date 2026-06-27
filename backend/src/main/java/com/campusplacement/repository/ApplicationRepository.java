package com.campusplacement.repository;

import com.campusplacement.entity.Application;
import com.campusplacement.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    long countByStudentId(
            Long studentId
    );

    long countByStudentIdAndApplicationStatus(
            Long studentId,
            ApplicationStatus applicationStatus
    );

    boolean existsByStudentIdAndJobId(
            Long studentId,
            Long jobId
    );

    long countByJobRecruiterId(
            Long recruiterId
    );

    long countByJobRecruiterIdAndApplicationStatus(
            Long recruiterId,
            ApplicationStatus applicationStatus
    );

    List<Application> findByStudentIdOrderByAppliedAtDesc(
            Long studentId
    );

    List<Application> findByJobIdOrderByAppliedAtDesc(
            Long jobId
    );

    long countByJobId(
            Long jobId
    );

    long countByJobIdAndApplicationStatus(
            Long jobId,
            ApplicationStatus applicationStatus
    );
}