package com.alterra.demo.repository;

import com.alterra.demo.domain.dao.ProductDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductDao,Long> {


}
