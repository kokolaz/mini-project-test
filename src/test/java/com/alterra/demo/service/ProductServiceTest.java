package com.alterra.demo.service;

import com.alterra.demo.domain.common.ApiResponse;
import com.alterra.demo.domain.dao.*;
import com.alterra.demo.domain.dto.*;
import com.alterra.demo.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductService.class)
public class ProductServiceTest {

    @MockBean
    private MenuRepository menuRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @org.junit.Test
    public void createProduct() throws Exception {
        MenuDao menuDao = MenuDao.builder()
                .id(1L)
                .name("Breakfast")
                .build();

        ProductDao productDao = ProductDao.builder()
                .id(1L)
                .name("Burger")
                .price(50000)
                .build();

        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .menuId(MenuDto.builder().id(1L).build())
                .name("Burger")
                .price(50000)
                .build();

        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menuDao));
        when(productRepository.save(any())).thenReturn(productDao);
        ResponseEntity<Object> responseEntity = productService.createNewProduct(ProductDto.builder()
                .menuId(MenuDto.builder()
                        .id(1L)
                        .build())
                .name("Burger")
                .price(50000)
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ProductDto data = (ProductDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals(productDto.getMenuId().getId(),data.getMenuId().getId());
        assertEquals("Burger",data.getName());
        assertEquals(50000,data.getPrice());
    }

    @Test
    public void updateMenu_Success() throws Exception {
        MenuDao menuDao = MenuDao.builder()
                .id(1L)
                .name("Breakfast")
                .build();

        ProductDao productDao = ProductDao.builder()
                .id(1L)
                .menuId(menuDao)
                .name("Burger")
                .price(50000)
                .build();
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .menuId(MenuDto.builder().id(1L).build())
                .name("Burger")
                .price(50000)
                .build();

        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menuDao));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productDao));
        when(productRepository.save(any())).thenReturn(productDao);
        ResponseEntity<Object> responseEntity = productService.updateProduct(anyLong(), ProductDto.builder()
                .menuId(MenuDto.builder()
                        .id(1L)
                        .build())
                .name("Katsu")
                .price(50000)
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ProductDto data = (ProductDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals(productDto.getMenuId().getId(),data.getId());
        assertEquals("Katsu",data.getName());
        assertEquals(50000,data.getPrice());
    }

    @Test
    public void updateProduct_Failed(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = productService.updateProduct(anyLong(),ProductDto.builder().build());

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void getProductById_Success(){
        MenuDao menuDao = MenuDao.builder()
                .id(1L)
                .name("Breakfast")
                .build();

        ProductDao productDao = ProductDao.builder()
                .id(1L)
                .menuId(menuDao)
                .name("Burger")
                .price(50000)
                .build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productDao));
        ResponseEntity<Object> responseEntity = productService.getProductById(anyLong());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void deleteProduct_Failed(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> product = productService.deleteProduct(anyLong());

        assertEquals(HttpStatus.BAD_REQUEST.value(),product.getStatusCodeValue());
    }

    @Test
    public void deleteProduct_Success(){
        MenuDao menuDao = MenuDao.builder()
                .id(1L)
                .name("Breakfast")
                .build();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(ProductDao.builder()
                .id(1L)
                .menuId(menuDao)
                .name("Burger")
                .price(50000)
                .build()));
        ResponseEntity<Object> product = productService.deleteProduct(anyLong());

        assertEquals(HttpStatus.OK.value(),product.getStatusCodeValue());
    }
}