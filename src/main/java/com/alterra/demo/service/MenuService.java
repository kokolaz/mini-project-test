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
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public ResponseEntity<Object> createNewMenu(MenuDto menu){
        Optional<RestaurantDao> restaurantDao = restaurantRepository.findById(menu.getRestaurantId().getId());
        if (restaurantDao.isEmpty())
            return ResponseUtil.build(KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);

        MenuDao menuDao = MenuDao
                .builder()
                .restaurantId(restaurantDao.get())
                .name(menu.getName())
                .build();

        menuDao = menuRepository.save(menuDao);
        return ResponseUtil.build(KEY_FOUND, MenuDto.builder()
                .id(menuDao.getId())
                .restaurantId(
                        RestaurantDto
                                .builder()
                                .id(restaurantDao.get().getId())
                                .restaurantName(restaurantDao.get().getRestaurantName())
                                .build()
                )
                .name(menuDao.getName())
                .build(), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getMenuById(Long id){
        Optional<MenuDao> menuDaoOptional = menuRepository.findById(id);
        if (menuDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null ,HttpStatus.BAD_REQUEST);
        }
        MenuDao menuDao = menuDaoOptional.get();

        return ResponseUtil.build(KEY_FOUND, MenuDto.builder()
                .id(menuDao.getId())
                .restaurantId(RestaurantDto.builder()
                        .id(menuDao.getRestaurantId().getId())
                        .restaurantName(menuDao.getRestaurantId().getRestaurantName())
                        .build())
                .name(menuDao.getName())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> updateMenu(Long id,MenuDto update){
        Optional<MenuDao> menuDaoOptional = menuRepository.findById(id);
        if (menuDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        }

        Optional<RestaurantDao> restaurantDaoOptional = restaurantRepository.findById(update.getRestaurantId().getId());
        if (restaurantDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        }

        MenuDao menuDao = menuDaoOptional.get();
        menuDao.setRestaurantId(restaurantDaoOptional.get());
        menuDao.setName(update.getName());
        menuRepository.save(menuDao);

        return ResponseUtil.build(KEY_FOUND,MenuDto.builder()
                .id(menuDao.getId())
                .restaurantId(RestaurantDto.builder()
                        .id(menuDao.getRestaurantId().getId())
                        .restaurantName(menuDao.getRestaurantId().getRestaurantName())
                        .build())
                .name(menuDao.getName())
                .build(),HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteMenu(Long id){
        Optional<MenuDao> menuDaoOptional = menuRepository.findById(id);

        if (menuDaoOptional.isEmpty()) {
            return ResponseUtil.build(KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }

        menuRepository.deleteById(id);
        return ResponseUtil.build(KEY_FOUND,null, HttpStatus.OK);
    }
}
