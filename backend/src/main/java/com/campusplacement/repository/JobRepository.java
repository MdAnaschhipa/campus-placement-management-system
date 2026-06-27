package com.campusplacement.repository;

import com.campusplacement.entity.Job;
import com.campusplacement.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByRecruiterIdOrderByCreatedAtDesc(
            Long recruiterId
    );

    List<Job> findByJobStatus(
            JobStatus jobStatus
    );

    long countByJobStatus(
            JobStatus jobStatus
    );

    long countByRecruiterId(
            Long recruiterId
    );

    long countByRecruiterIdAndJobStatus(
            Long recruiterId,
            JobStatus jobStatus
    );
}