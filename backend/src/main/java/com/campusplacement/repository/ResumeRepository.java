package com.campusplacement.repository;

import com.campusplacement.entity.Resume;
import com.campusplacement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findByStudent(Student student);

    boolean existsByStudent(Student student);
}