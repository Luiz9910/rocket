package com.gestaovagas.vagas.modules.company.controller;

import com.gestaovagas.vagas.modules.company.entities.CompanyEntity;
import com.gestaovagas.vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping
    public CompanyEntity create(@RequestBody @Valid CompanyEntity companyEntity) {
        return this.createCompanyUseCase.execute(companyEntity);
    }
}
