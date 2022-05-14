package com.alterra.demo.controller;
import com.alterra.demo.domain.dto.ProductDto;

import com.alterra.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody ProductDto product) {
        return productService.createNewProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ProductDto product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
