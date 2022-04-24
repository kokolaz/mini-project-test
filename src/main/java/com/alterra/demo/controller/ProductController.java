package com.alterra.demo.controller;

import com.alterra.demo.domain.dto.ProductDto;
import com.alterra.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllPost(){
        return productService.getAllPost();
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> saveNewPost(@RequestBody ProductDto prod){
        return productService.saveNewProduct(prod);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto,@PathVariable Long id){
        return productService.updateProduct(id,productDto);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }


}
