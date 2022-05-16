package com.alterra.demo.service;

import com.alterra.demo.domain.model.*;
import com.alterra.demo.repository.AdminRepository;
import com.alterra.demo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Admin register(UsernamePassword req) {
        Admin admin = new Admin();
        admin.setUsername(req.getUsername());
        admin.setPassword(passwordEncoder.encode(req.getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public TokenResponse generateToken(UsernamePassword req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generationToken(authentication);
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(jwt);
            return tokenResponse;
        } catch (BadCredentialsException e){
            log.error("Bad credential", e);
            throw new RuntimeException(e.getMessage(), e);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public UsernameResponse generateUsername(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String token = bearerToken.substring(7);

        UsernameResponse user = new UsernameResponse();
        user.setUsername(jwtTokenProvider.getUsername(token));
        return user;
    }
}
