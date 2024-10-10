package com.gestaovagas.vagas.modules.candidate.controller;

import com.gestaovagas.vagas.modules.candidate.CandidateEntity;
import com.gestaovagas.vagas.modules.candidate.useCases.CreateCandidadeUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("candidate")
public class CandidateController {
    @Autowired
    private CreateCandidadeUseCase createCandidadeUseCase;

    @PostMapping
    public CandidateEntity create(@RequestBody @Valid CandidateEntity candidate) {
        return this.createCandidadeUseCase.execute(candidate);
    }
}
