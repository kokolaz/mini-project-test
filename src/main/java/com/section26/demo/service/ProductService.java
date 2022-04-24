package com.section26.demo.service;

import java.util.List;

import com.section26.demo.model.Product;

public interface ProductService {
    Product create (Product product);
    List<Product> findAll();
    Product findById(String id);
    Product update(String id, Product product);
    void delete(String id);
}
