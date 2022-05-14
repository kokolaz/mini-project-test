package com.alterra.demo.repository;

import com.alterra.demo.domain.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin getDistinctTopByUsername(String username);
}
