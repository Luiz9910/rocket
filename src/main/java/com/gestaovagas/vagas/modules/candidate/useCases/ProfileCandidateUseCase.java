package com.gestaovagas.vagas.modules.candidate.useCases;

import com.gestaovagas.vagas.modules.candidate.CandidateEntity;
import com.gestaovagas.vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    public void execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate);
    }
}
