package com.gestaovagas.vagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gestaovagas.vagas.modules.company.dto.AuthCompanyDTO;
import com.gestaovagas.vagas.modules.company.entities.CompanyEntity;
import com.gestaovagas.vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gestaovagas.vagas.exceptions.UserFoundException;

import javax.naming.AuthenticationException;
import javax.swing.text.html.Option;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
public class AuthCompanyUseCase {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDTO) {
        var company = this.companyRepository.findByUsernameOrEmail(authCompanyDTO.getUsername().trim(), "sei nao")
                .orElseThrow(() -> new UsernameNotFoundException("dasda"));

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if (!passwordMatches) {
            try {
                throw new AuthenticationException("");
            } catch (AuthenticationException e) {
                throw new RuntimeException(e);
            }
        }

        Algorithm algorithm = Algorithm.HMAC256("dmasuidmasifm");

        return JWT.create().withIssuer("javagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(String.valueOf(company.getId()))
                .sign(algorithm);
    }
}
