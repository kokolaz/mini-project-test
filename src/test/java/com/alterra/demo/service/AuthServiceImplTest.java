package com.alterra.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.alterra.demo.domain.model.Admin;
import com.alterra.demo.domain.model.UsernamePassword;
import com.alterra.demo.repository.AdminRepository;

import com.alterra.demo.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(classes = AuthServiceImpl.class)
class AuthServiceImplTest {

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @Test
    void register_Success() {
        when(adminRepository.getDistinctTopByUsername("laz")).thenReturn(null);
        when(adminRepository.save(any())).thenReturn(Admin.builder()
                .id(1L)
                .username("laz")
                .password("12345678")
                .build());

        Admin admin = authService.register(UsernamePassword.builder()
                .username("laz")
                .password("12345678")
                .build()
        );
        assertEquals(1L, admin.getId());
        assertEquals("laz", admin.getUsername());
        assertEquals("12345678", admin.getPassword());
    }

    @Test
    void generateToken() {

    }

    @Test
    void generateUsername() {

    }
}