package com.gestaovagas.vagas.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        SecurityContextHolder.getContext().setAuthentication(null);

        String header = request.getHeader("Authorization");
        String tokenHeader = header != null ? header.replace("Bearer ", "") : null;

        if (request.getRequestURI().startsWith("/candidate")) {
            if (tokenHeader != null) {
                Algorithm algorithm = Algorithm.HMAC256("dasdasdsa");

                try {
                    DecodedJWT decodedJWT = JWT.require(algorithm)
                            .build()
                            .verify(tokenHeader);

                    String tokenSubject = decodedJWT.getSubject();

                    if  (tokenSubject != null && !tokenSubject.isEmpty()) {
                        request.setAttribute("company_id", tokenSubject);
                        var roles = decodedJWT.getClaim("roles").asList(String.class);

                        var grants = roles.stream()
                                .map(
                                        role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())
                                ).toList();

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(tokenSubject,
                                null, grants);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                } catch (JWTVerificationException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }


        filterChain.doFilter(request, response);
    }
}
