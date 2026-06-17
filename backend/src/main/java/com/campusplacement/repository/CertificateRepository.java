package com.campusplacement.repository;

import com.campusplacement.entity.Certificate;
import com.campusplacement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findByStudent(Student student);
}