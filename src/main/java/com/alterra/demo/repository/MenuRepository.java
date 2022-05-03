package com.alterra.demo.repository;

import com.alterra.demo.domain.dao.MenuDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuDao,Long> {
}
