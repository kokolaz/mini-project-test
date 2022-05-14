package com.alterra.demo.service;
import com.alterra.demo.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.alterra.demo.constant.ConstantApp.KEY_FOUND;
import static com.alterra.demo.constant.ConstantApp.KEY_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.alterra.demo.domain.dao.*;
import com.alterra.demo.domain.dto.*;
import com.alterra.demo.repository.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MenuRepository menuRepository;

    public ResponseEntity<Object> createNewProduct(ProductDto product){
        Optional<MenuDao> menuDao = menuRepository.findById(product.getMenuId().getId());
        if (menuDao.isEmpty())
            return ResponseUtil.build(KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);

        ProductDao productDao = ProductDao
                .builder()
                .menuId(menuDao.get())
                .name(product.getName())
                .price(product.getPrice())
                .build();

        productDao = productRepository.save(productDao);
        return ResponseUtil.build(KEY_FOUND, ProductDto.builder()
                .id(productDao.getId())
                .menuId(
                        MenuDto
                                .builder()
                                .id(menuDao.get().getId())
                                .name(menuDao.get().getName())
                                .build()
                )
                .name(productDao.getName())
                .price(productDao.getPrice())
                .build(), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getProductById(Long id){
        Optional<ProductDao> productDaoOptional = productRepository.findById(id);
        if (productDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null ,HttpStatus.BAD_REQUEST);
        }
        ProductDao productDao = productDaoOptional.get();

        return ResponseUtil.build(KEY_FOUND, ProductDto.builder()
                .id(productDao.getId())
                .menuId(MenuDto.builder()
                        .id(productDao.getMenuId().getId())
                        .name(productDao.getMenuId().getName())
                        .build())
                .name(productDao.getName())
                .price(productDao.getPrice())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> updateProduct(Long id,ProductDto update){
        Optional<ProductDao> productDaoOptional = productRepository.findById(id);
        if (productDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        }

        Optional<MenuDao> menuDaoOptional = menuRepository.findById(update.getMenuId().getId());
        if (menuDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        }

        ProductDao productDao = productDaoOptional.get();
        productDao.setMenuId(menuDaoOptional.get());
        productDao.setName(update.getName());
        productRepository.save(productDao);

        return ResponseUtil.build(KEY_FOUND,ProductDto.builder()
                .id(productDao.getId())
                .menuId(MenuDto.builder()
                        .id(productDao.getMenuId().getId())
                        .name(productDao.getMenuId().getName())
                        .build())
                .name(productDao.getName())
                .price(productDao.getPrice())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteProduct(Long id){
        Optional<ProductDao> productDaoOptional = productRepository.findById(id);

        if (productDaoOptional.isEmpty()) {
            return ResponseUtil.build(KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }

        productRepository.deleteById(id);
        return ResponseUtil.build(KEY_FOUND,null, HttpStatus.OK);
    }
}
