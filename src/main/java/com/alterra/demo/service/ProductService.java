package com.alterra.demo.service;
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
import com.alterra.demo.response.BaseResponse;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MenuRepository menuRepository;

    public ResponseEntity<Object> getAllProduct(){
        List<ProductDao> productDaoList = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();

        for(ProductDao productDao: productDaoList){
            Optional<MenuDao> menuDaoOptional = menuRepository.findById(productDao.getMenuId().getId());
            productDtoList.add(ProductDto.builder()
                    .id(productDao.getId())
                    .menuId(MenuDto.builder()
                            .id(menuDaoOptional.get().getId())
                            .name(menuDaoOptional.get().getName())
                            .build())
                    .name(productDao.getName())
                    .price(productDao.getPrice())
                    .build());
        }

        return BaseResponse.build(HttpStatus.OK, KEY_FOUND, productDtoList);
    }

    public ResponseEntity<Object> createNewProduct(ProductDto product){
        Optional<MenuDao> menuDao = menuRepository.findById(product.getMenuId().getId());
        if (menuDao.isEmpty())
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);

        ProductDao productDao = ProductDao
                .builder()
                .menuId(menuDao.get())
                .name(product.getName())
                .price(product.getPrice())
                .build();

        productRepository.save(productDao);
        return BaseResponse.build(HttpStatus.CREATED, KEY_FOUND, ProductDto.builder()
                .id(productDao.getId())
                .menuId(
                        MenuDto
                                .builder()
                                .id(menuDao.get().getId())
                                .name(menuDao.get().getName())
                                .build()
                )
                .name(productDao.getName())
                .build());
    }

    public ResponseEntity<Object> updateProduct(Long id,ProductDto update){
        Optional<ProductDao> productDaoOptional = productRepository.findById(id);
        if (productDaoOptional.isEmpty()){
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);
        }

        Optional<MenuDao> menuDaoOptional = menuRepository.findById(update.getMenuId().getId());
        if (menuDaoOptional.isEmpty()){
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);
        }

        ProductDao productDao = productDaoOptional.get();
        productDao.setMenuId(menuDaoOptional.get());
        productDao.setName(update.getName());
        productRepository.save(productDao);

        return BaseResponse.build(HttpStatus.OK,KEY_FOUND,ProductDto.builder()
                .id(productDao.getId())
                .menuId(MenuDto.builder()
                        .id(productDao.getMenuId().getId())
                        .name(productDao.getMenuId().getName())
                        .build())
                .name(productDao.getName())
                .build());
    }

    public ResponseEntity<Object> deleteProduct(Long id){
        Optional<ProductDao> productDaoOptional = productRepository.findById(id);

        if (productDaoOptional.isEmpty()) {
            return BaseResponse.build(HttpStatus.BAD_REQUEST, KEY_NOT_FOUND, null);
        }

        productRepository.deleteById(id);
        return BaseResponse.build(HttpStatus.OK,KEY_FOUND,null);
    }
}
