package com.campusplacement.service.impl;

import com.campusplacement.dto.request.SkillRequest;
import com.campusplacement.dto.response.SkillResponse;
import com.campusplacement.entity.Skill;
import com.campusplacement.entity.Student;
import com.campusplacement.entity.User;
import com.campusplacement.repository.SkillRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public SkillResponse addSkill(SkillRequest request) {

        Student student = resolveCurrentStudent();

        String skillName = request.getSkillName().trim();

        if (skillRepository.existsByStudentAndSkillName(student, skillName)) {
            throw new IllegalArgumentException(
                    "Skill '" + skillName + "' already exists in your profile."
            );
        }

        Skill skill = Skill.builder()
                .student(student)
                .skillName(skillName)
                .build();

        Skill savedSkill = skillRepository.save(skill);

        return toResponse(savedSkill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponse> getMySkills() {

        Student student = resolveCurrentStudent();

        return skillRepository.findByStudent(student)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public SkillResponse updateSkill(Long skillId, SkillRequest request) {

        Student student = resolveCurrentStudent();

        Skill skill = resolveOwnedSkill(skillId, student);

        String skillName = request.getSkillName().trim();

        if (!skill.getSkillName().equals(skillName)
                && skillRepository.existsByStudentAndSkillName(student, skillName)) {

            throw new IllegalArgumentException(
                    "Skill '" + skillName + "' already exists in your profile."
            );
        }

        skill.setSkillName(skillName);

        Skill updatedSkill = skillRepository.save(skill);

        return toResponse(updatedSkill);
    }

    @Override
    public void deleteSkill(Long skillId) {

        Student student = resolveCurrentStudent();

        Skill skill = resolveOwnedSkill(skillId, student);

        skillRepository.delete(skill);
    }

    private Student resolveCurrentStudent() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException(
                    "No authenticated user found in security context."
            );
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found: " + email
                        )
                );

        return studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found. Please create your profile first."
                        )
                );
    }

    private Skill resolveOwnedSkill(Long skillId, Student student) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Skill not found with id: " + skillId
                        )
                );

        if (!skill.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException(
                    "You do not have permission to modify this skill."
            );
        }

        return skill;
    }

    private SkillResponse toResponse(Skill skill) {

        return SkillResponse.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .createdAt(skill.getCreatedAt())
                .updatedAt(skill.getUpdatedAt())
                .build();
    }
}