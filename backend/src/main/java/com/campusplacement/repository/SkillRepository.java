package com.campusplacement.repository;

import com.campusplacement.entity.Skill;
import com.campusplacement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByStudent(Student student);

    boolean existsByStudent(Student student);

    boolean existsByStudentAndSkillName(
            Student student,
            String skillName
    );

    void deleteByIdAndStudent(
            Long id,
            Student student
    );
}