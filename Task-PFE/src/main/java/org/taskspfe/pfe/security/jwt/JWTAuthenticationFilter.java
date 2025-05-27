package org.taskspfe.pfe.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.taskspfe.pfe.exceptions.ExpiredTokenException;
import org.taskspfe.pfe.exceptions.InvalidTokenException;
import org.taskspfe.pfe.exceptions.ResourceNotFoundException;
import org.taskspfe.pfe.exceptions.RevokedTokenException;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.repository.TokenRepository;
import org.taskspfe.pfe.security.utility.CustomUserDetailsService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {


    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public JWTAuthenticationFilter(JWTService jwtService, TokenRepository tokenRepository, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        try {

            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String jwtToken = authHeader.substring(7);

            if (!jwtService.validateToken(jwtToken)) {
                filterChain.doFilter(request, response);
                return;
            }


            String userEmail = jwtService.extractEmailFromJwt(jwtToken);

            if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserEntity userEntity = (UserEntity) this.customUserDetailsService.loadUserByUsername(userEmail);

            var isTokenValid = tokenRepository.findByToken(jwtToken).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
            var tokenSaved = tokenRepository.findByToken(jwtToken).orElse(null);
            if (!isTokenValid) {
                throw new ResourceNotFoundException("Token not found.");
            }

            if (jwtService.isTokenExpired(jwtToken)) {
                throw new ExpiredTokenException("Token has expired.");
            }
            if (tokenSaved.isRevoked()) {
                throw new RevokedTokenException("Token has been revoked");
            }
            if (!jwtService.isTokenValid(jwtToken, userEntity)) {
                throw new InvalidTokenException("Invalid token");
            }


            if (!jwtService.isTokenValid(jwtToken, userEntity) || !isTokenValid || !userEntity.isEnabled()) {
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        }
        catch (Exception e)
        {
            log.error("Error logging in: {}",e.getMessage());
            response.setHeader("error",e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Map<String, String> error = new HashMap<>();
            error.put("time_stamp" , String.valueOf(LocalDateTime.now()));
            error.put("status" , String.valueOf(HttpServletResponse.SC_FORBIDDEN));
            error.put("error_message", e.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),error);
        }
    }
}
