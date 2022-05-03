package com.alterra.demo.repository;

import com.alterra.demo.domain.dao.CityDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityDao,Long> {
}
