package com.alterra.demo.repository;

import com.alterra.demo.domain.dao.RestaurantDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantDao,Long> {
}
