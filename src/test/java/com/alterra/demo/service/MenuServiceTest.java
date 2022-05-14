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
@SpringBootTest(classes = MenuService.class)
public class MenuServiceTest {

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @Test
    public void createMenu() throws Exception {
        RestaurantDao restaurantDao = RestaurantDao.builder()
                .id(1L)
                .restaurantName("McDonald")
                .build();

        MenuDao menuDao = MenuDao.builder()
                .id(1L)
                .name("Breakfast")
                .build();

        MenuDto menuDto = MenuDto.builder()
                .id(1L)
                .restaurantId(RestaurantDto.builder().id(1L).build())
                .name("Breakfast")
                .build();

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantDao));
        when(menuRepository.save(any())).thenReturn(menuDao);
        ResponseEntity<Object> responseEntity = menuService.createNewMenu(MenuDto.builder()
                .restaurantId(RestaurantDto.builder()
                        .id(1L)
                        .build())
                .name("Breakfast")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        MenuDto data = (MenuDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals(menuDto.getRestaurantId().getId(),data.getRestaurantId().getId());
        assertEquals("Breakfast",data.getName());
    }

    @Test
    public void updateMenu_Success() throws Exception {
        RestaurantDao restaurantDao = RestaurantDao.builder()
                .id(1L)
                .restaurantName("McDonald")
                .build();

        MenuDao menuDao = MenuDao.builder()
                .id(1L)
                .restaurantId(restaurantDao)
                .name("Breakfast")
                .build();
        MenuDto menuDto = MenuDto.builder()
                .id(1L)
                .restaurantId(RestaurantDto.builder().id(1L).build())
                .name("Laz")
                .build();

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantDao));
        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menuDao));
        when(menuRepository.save(any())).thenReturn(menuDao);
        ResponseEntity<Object> responseEntity = menuService.updateMenu(anyLong(), MenuDto.builder()
                .restaurantId(RestaurantDto.builder()
                        .id(1L)
                        .build())
                .name("Lunch")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        MenuDto data = (MenuDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals(menuDto.getRestaurantId().getId(),data.getId());
        assertEquals("Lunch",data.getName());
    }

    @Test
    public void updateMenu_Failed(){
        when(menuRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = menuService.updateMenu(anyLong(),MenuDto.builder().build());

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void getMenuById_Success(){
        RestaurantDao restaurantDao = RestaurantDao.builder()
                .id(1L)
                .restaurantName("McDonald")
                .build();

        MenuDao menuDao = MenuDao.builder()
                .id(1L)
                .restaurantId(restaurantDao)
                .name("Breakfast")
                .build();
        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menuDao));
        ResponseEntity<Object> responseEntity = menuService.getMenuById(anyLong());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void deleteMenu_Failed(){
        when(menuRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> menu = menuService.deleteMenu(anyLong());

        assertEquals(HttpStatus.BAD_REQUEST.value(),menu.getStatusCodeValue());
    }

    @Test
    public void deleteMenu_Success(){
        RestaurantDao restaurantDao = RestaurantDao.builder()
                .id(1L)
                .restaurantName("McDonald")
                .build();

        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(MenuDao.builder()
                .id(1L)
                .restaurantId(restaurantDao)
                .name("McDonald")
                .build()));
        ResponseEntity<Object> menu = menuService.deleteMenu(anyLong());

        assertEquals(HttpStatus.OK.value(),menu.getStatusCodeValue());
    }

}