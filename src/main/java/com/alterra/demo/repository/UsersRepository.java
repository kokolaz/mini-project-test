package com.alterra.demo.repository;

import com.alterra.demo.domain.dao.UsersDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersDao,Long> {
}
