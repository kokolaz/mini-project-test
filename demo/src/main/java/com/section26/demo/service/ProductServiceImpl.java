package com.section26.demo.service;

import java.util.List;
import com.section26.demo.model.Product;
import com.section26.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product){
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Override
    public Product findById(String id){
        return productRepository.findById(id).orElseThrow(() -> {
            throw new Error("Product with ID " + id + " is not found.");
        });
    }

    @Override
    public Product update(String id, Product product){
        Product productById = this.findById(id);
        productById.setName(product.getName());
        productById.setPrice(product.getPrice());
        return productRepository.save(productById);
    }

    @Override
    public void delete(String id){
        Product productById = this.findById(id);
        productRepository.delete(productById);
    }
}
