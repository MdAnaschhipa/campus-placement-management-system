package com.campusplacement.service;

import com.campusplacement.dto.response.ResumeResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {

    ResumeResponse uploadResume(MultipartFile file);

    ResumeResponse getMyResume();

    void deleteResume();
}