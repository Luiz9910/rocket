package com.gestaovagas.vagas.modules.candidate.controller;

import com.gestaovagas.vagas.modules.candidate.CandidateEntity;
import com.gestaovagas.vagas.modules.candidate.useCases.CreateCandidadeUseCase;
import com.gestaovagas.vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("candidate")
public class CandidateController {
    @Autowired
    private CreateCandidadeUseCase createCandidadeUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @PostMapping
    public CandidateEntity create(@RequestBody @Valid CandidateEntity candidate) {
        return this.createCandidadeUseCase.execute(candidate);
    }

    @GetMapping
    @PreAuthorize("hasRole('candidate')")
    public ResponseEntity<?> get(HttpServletRequest request) {
        var idCandidate =    request.getAttribute("candidate_id");

        var profile = this.profileCandidateUseCase
                .execute(UUID.fromString(idCandidate.toString()));
        return ResponseEntity.ok().body(profile);
    }
}
