package com.campusplacement.repository;

import com.campusplacement.entity.Student;
import com.campusplacement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUser(User user);

    Optional<Student> findByEnrollmentNumber(String enrollmentNumber);

    boolean existsByEnrollmentNumber(String enrollmentNumber);

    boolean existsByUser(User user);
}