package com.gestaovagas.vagas.modules.company.useCases;

import com.gestaovagas.vagas.exceptions.UserFoundException;
import com.gestaovagas.vagas.modules.company.entities.CompanyEntity;
import com.gestaovagas.vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(),
                companyEntity.getEmail())
                .ifPresent((company) -> {
                    throw new UserFoundException();
                });

        companyEntity.setPassword(passwordEncoder.encode(companyEntity.getPassword()));

        return this.companyRepository.save(companyEntity);
    }
}
