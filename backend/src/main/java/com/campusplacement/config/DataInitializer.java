package com.campusplacement.config;

import com.campusplacement.entity.Branch;
import com.campusplacement.entity.CompanySize;
import com.campusplacement.entity.Industry;
import com.campusplacement.repository.BranchRepository;
import com.campusplacement.repository.CompanySizeRepository;
import com.campusplacement.repository.IndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BranchRepository branchRepository;
    private final IndustryRepository industryRepository;
    private final CompanySizeRepository companySizeRepository;

    @Override
    public void run(String... args) {

        seedBranches();
        seedIndustries();
        seedCompanySizes();
    }

    private void seedBranches() {

        if (branchRepository.count() > 0) {
            return;
        }

        branchRepository.saveAll(List.of(

                Branch.builder()
                        .branchCode("CSE")
                        .branchName("Computer Science Engineering")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("CS_AI")
                        .branchName("Computer Science Engineering with Specialization in Artificial Intelligence")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("CS_DS")
                        .branchName("Computer Science Engineering with Specialization in Data Science")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("CS_CYBER")
                        .branchName("Computer Science Engineering with Specialization in Cyber Security")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("CS_IOT")
                        .branchName("Computer Science Engineering with Specialization in Internet of Things")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("CSBS")
                        .branchName("Computer Science and Business Systems")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("IT")
                        .branchName("Information Technology")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("AIML")
                        .branchName("Artificial Intelligence and Machine Learning")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("ECE")
                        .branchName("Electronics and Communication Engineering")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("EEE")
                        .branchName("Electrical and Electronics Engineering")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("EE")
                        .branchName("Electrical Engineering")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("ME")
                        .branchName("Mechanical Engineering")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("CE")
                        .branchName("Civil Engineering")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("CHE")
                        .branchName("Chemical Engineering")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("BCA")
                        .branchName("Bachelor of Computer Applications")
                        .isActive(true)
                        .build(),

                Branch.builder()
                        .branchCode("MCA")
                        .branchName("Master of Computer Applications")
                        .isActive(true)
                        .build()
        ));
    }

    private void seedIndustries() {

        if (industryRepository.count() > 0) {
            return;
        }

        industryRepository.saveAll(List.of(

                Industry.builder().industryName("Information Technology").isActive(true).build(),
                Industry.builder().industryName("Software Development").isActive(true).build(),
                Industry.builder().industryName("Artificial Intelligence").isActive(true).build(),
                Industry.builder().industryName("Machine Learning").isActive(true).build(),
                Industry.builder().industryName("Cyber Security").isActive(true).build(),
                Industry.builder().industryName("Cloud Computing").isActive(true).build(),
                Industry.builder().industryName("Data Analytics").isActive(true).build(),
                Industry.builder().industryName("FinTech").isActive(true).build(),
                Industry.builder().industryName("EdTech").isActive(true).build(),
                Industry.builder().industryName("E-Commerce").isActive(true).build(),
                Industry.builder().industryName("Healthcare").isActive(true).build(),
                Industry.builder().industryName("Manufacturing").isActive(true).build(),
                Industry.builder().industryName("Banking").isActive(true).build(),
                Industry.builder().industryName("Telecommunications").isActive(true).build()
        ));
    }

    private void seedCompanySizes() {

        if (companySizeRepository.count() > 0) {
            return;
        }

        companySizeRepository.saveAll(List.of(

                CompanySize.builder()
                        .sizeName("Startup")
                        .isActive(true)
                        .build(),

                CompanySize.builder()
                        .sizeName("Small")
                        .isActive(true)
                        .build(),

                CompanySize.builder()
                        .sizeName("Medium")
                        .isActive(true)
                        .build(),

                CompanySize.builder()
                        .sizeName("Large")
                        .isActive(true)
                        .build(),

                CompanySize.builder()
                        .sizeName("Enterprise")
                        .isActive(true)
                        .build()
        ));
    }
}