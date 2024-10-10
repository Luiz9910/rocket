package com.gestaovagas.vagas.modules.company.controller;

import com.gestaovagas.vagas.modules.company.dto.CreateJobDTO;
import com.gestaovagas.vagas.modules.company.entities.JobEntity;
import com.gestaovagas.vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("job")
public class JobController {
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping
    public JobEntity create(@RequestBody @Valid CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        var jobEntity = JobEntity.builder().benefits(createJobDTO.getBenefits())
                        .description(createJobDTO.getDescription())
                                .level(createJobDTO.getLevel())
                                        .build();

        jobEntity.setCompanyId(UUID.fromString(companyId.toString()));
        return this.createJobUseCase.execute(jobEntity);

    }
}
