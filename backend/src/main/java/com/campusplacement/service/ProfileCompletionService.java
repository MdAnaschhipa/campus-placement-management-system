package com.campusplacement.service;

import com.campusplacement.entity.Student;
import com.campusplacement.repository.ProjectRepository;
import com.campusplacement.repository.SkillRepository;
import com.campusplacement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileCompletionService {

    private static final double PERSONAL_INFO_WEIGHT = 30.0;
    private static final double ACADEMIC_INFO_WEIGHT = 20.0;
    private static final double SKILLS_WEIGHT = 20.0;
    private static final double PROJECTS_WEIGHT = 10.0;
    private static final double RESUME_WEIGHT = 20.0;

    private static final int PERSONAL_INFO_FIELDS = 5;
    private static final int ACADEMIC_INFO_FIELDS = 3;

    private final StudentRepository studentRepository;
    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;

    public int calculateCompletion(Student student) {

        double score = 0.0;

        score += calculatePersonalInfoScore(student);
        score += calculateAcademicInfoScore(student);

        if (skillRepository.existsByStudent(student)) {
            score += SKILLS_WEIGHT;
        }

        if (projectRepository.existsByStudent(student)) {
            score += PROJECTS_WEIGHT;
        }

        if (isPresent(student.getResumeUrl())) {
            score += RESUME_WEIGHT;
        }

        return (int) Math.round(score);
    }

    public boolean isEligibleToApply(Student student) {

        return isPersonalInfoComplete(student)
                && isAcademicInfoComplete(student)
                && skillRepository.existsByStudent(student)
                && isPresent(student.getResumeUrl());
    }

    public Student updateCompletion(Student student) {

        int percentage = calculateCompletion(student);

        student.setProfileCompletionPercentage(percentage);

        return studentRepository.save(student);
    }

    private double calculatePersonalInfoScore(Student student) {

        int completedFields = 0;

        if (isPresent(student.getPhoneNumber())) completedFields++;
        if (student.getDateOfBirth() != null) completedFields++;
        if (student.getGender() != null) completedFields++;
        if (isPresent(student.getAddress())) completedFields++;
        if (isPresent(student.getProfilePhotoUrl())) completedFields++;

        return (PERSONAL_INFO_WEIGHT / PERSONAL_INFO_FIELDS) * completedFields;
    }

    private double calculateAcademicInfoScore(Student student) {

        int completedFields = 0;

        if (isPresent(student.getEnrollmentNumber())) completedFields++;
        if (student.getBranch() != null) completedFields++;
        if (student.getSemester() != null) completedFields++;

        return (ACADEMIC_INFO_WEIGHT / ACADEMIC_INFO_FIELDS) * completedFields;
    }

    private boolean isPersonalInfoComplete(Student student) {

        return isPresent(student.getPhoneNumber())
                && student.getDateOfBirth() != null
                && student.getGender() != null
                && isPresent(student.getAddress())
                && isPresent(student.getProfilePhotoUrl());
    }

    private boolean isAcademicInfoComplete(Student student) {

        return isPresent(student.getEnrollmentNumber())
                && student.getBranch() != null
                && student.getSemester() != null;
    }

    private boolean isPresent(String value) {
        return value != null && !value.isBlank();
    }
}