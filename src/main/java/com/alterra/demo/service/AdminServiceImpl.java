package com.alterra.demo.service;

import com.alterra.demo.domain.model.Admin;
import com.alterra.demo.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.getDistinctTopByUsername(username);
        if (admin == null)
            throw new UsernameNotFoundException("Username doesn't exist.");

        return admin;
    }
}
