package com.gestaovagas.vagas.modules.candidate.useCases;

import com.gestaovagas.vagas.exceptions.UserFoundException;
import com.gestaovagas.vagas.modules.candidate.CandidateEntity;
import com.gestaovagas.vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidadeUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidate) {
        this.candidateRepository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail()).
                ifPresent((user) -> {
                    throw new UserFoundException();
                });

        candidate.setPassword(passwordEncoder.encode(candidate.getPassword()));
        return this.candidateRepository.save(candidate);
    }
}
