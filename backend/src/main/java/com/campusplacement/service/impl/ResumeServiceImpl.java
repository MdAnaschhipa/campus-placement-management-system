package com.campusplacement.service.impl;

import com.campusplacement.dto.response.ResumeResponse;
import com.campusplacement.entity.Resume;
import com.campusplacement.entity.Student;
import com.campusplacement.entity.User;
import com.campusplacement.repository.ResumeRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.ProfileCompletionService;
import com.campusplacement.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private static final long MAX_FILE_SIZE_BYTES = 5L * 1024 * 1024;

    private final ResumeRepository resumeRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ProfileCompletionService profileCompletionService;

    @Override
    public ResumeResponse uploadResume(MultipartFile file) {

        Student student = resolveCurrentStudent();

        validateFile(file);

        Resume resume = resumeRepository
                .findByStudent(student)
                .orElse(null);

        String fileName = file.getOriginalFilename().trim();

        String filePath =
                "/uploads/resumes/" +
                        fileName.replace(" ", "_");

        if (resume == null) {

            resume = Resume.builder()
                    .student(student)
                    .fileName(fileName)
                    .filePath(filePath)
                    .fileSize(file.getSize())
                    .build();

        } else {

            resume.setFileName(fileName);
            resume.setFilePath(filePath);
            resume.setFileSize(file.getSize());
        }

        Resume savedResume = resumeRepository.save(resume);

        // Sync Student table
        student.setResumeUrl(filePath);
        studentRepository.save(student);

        // Recalculate profile completion
        profileCompletionService.updateCompletion(student);

        return toResponse(savedResume);
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeResponse getMyResume() {

        Student student = resolveCurrentStudent();

        Resume resume = resumeRepository.findByStudent(student)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resume not found. Please upload your resume first."
                        )
                );

        return toResponse(resume);
    }

    @Override
    public void deleteResume() {

        Student student = resolveCurrentStudent();

        Resume resume = resumeRepository.findByStudent(student)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resume not found. Please upload your resume first."
                        )
                );

        resumeRepository.delete(resume);

        // Remove from Student table
        student.setResumeUrl(null);
        studentRepository.save(student);

        // Recalculate profile completion
        profileCompletionService.updateCompletion(student);
    }

    private Student resolveCurrentStudent() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

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

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Resume file is required."
            );
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null ||
                !fileName.toLowerCase().endsWith(".pdf")) {

            throw new IllegalArgumentException(
                    "Only PDF files are allowed."
            );
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {

            throw new IllegalArgumentException(
                    "Resume file size must not exceed 5 MB."
            );
        }
    }

    private ResumeResponse toResponse(Resume resume) {

        return ResumeResponse.builder()
                .id(resume.getId())
                .fileName(resume.getFileName())
                .fileSize(resume.getFileSize())
                .downloadUrl("/api/student/resume/download")
                .createdAt(resume.getCreatedAt())
                .updatedAt(resume.getUpdatedAt())
                .build();
    }
}