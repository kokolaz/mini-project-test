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
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public ResponseEntity<Object> getAllMenu(){
        List<MenuDao> menuDaoList = menuRepository.findAll();
        List<MenuDto> menuDtoList = new ArrayList<>();

        for(MenuDao menuDao: menuDaoList){
            Optional<RestaurantDao> restaurantDaoOptional = restaurantRepository.findById(menuDao.getRestaurantId().getId());
            menuDtoList.add(MenuDto.builder()
                    .id(menuDao.getId())
                    .restaurantId(RestaurantDto.builder()
                            .id(restaurantDaoOptional.get().getId())
                            .restaurantName(restaurantDaoOptional.get().getRestaurantName())
                            .build())
                    .name(menuDao.getName())
                    .build());
        }

        return BaseResponse.build(HttpStatus.OK, KEY_FOUND, menuDtoList);
    }

    public ResponseEntity<Object> createNewMenu(MenuDto menu){
        Optional<RestaurantDao> restaurantDao = restaurantRepository.findById(menu.getRestaurantId().getId());
        if (restaurantDao.isEmpty())
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);

        MenuDao menuDao = MenuDao
                .builder()
                .restaurantId(restaurantDao.get())
                .name(menu.getName())
                .build();

        menuRepository.save(menuDao);
        return BaseResponse.build(HttpStatus.CREATED, KEY_FOUND, MenuDto.builder()
                .id(menuDao.getId())
                .restaurantId(
                        RestaurantDto
                                .builder()
                                .id(restaurantDao.get().getId())
                                .restaurantName(restaurantDao.get().getRestaurantName())
                                .build()
                )
                .name(menuDao.getName())
                .build());
    }

    public ResponseEntity<Object> updateMenu(Long id,MenuDto update){
        Optional<MenuDao> menuDaoOptional = menuRepository.findById(id);
        if (menuDaoOptional.isEmpty()){
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);
        }

        Optional<RestaurantDao> restaurantDaoOptional = restaurantRepository.findById(update.getRestaurantId().getId());
        if (restaurantDaoOptional.isEmpty()){
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);
        }

        MenuDao menuDao = menuDaoOptional.get();
        menuDao.setRestaurantId(restaurantDaoOptional.get());
        menuDao.setName(update.getName());
        menuRepository.save(menuDao);

        return BaseResponse.build(HttpStatus.OK,KEY_FOUND,MenuDto.builder()
                .id(menuDao.getId())
                .restaurantId(RestaurantDto.builder()
                        .id(menuDao.getRestaurantId().getId())
                        .restaurantName(menuDao.getRestaurantId().getRestaurantName())
                        .build())
                .name(menuDao.getName())
                .build());
    }

    public ResponseEntity<Object> deleteMenu(Long id){
        Optional<MenuDao> menuDaoOptional = menuRepository.findById(id);

        if (menuDaoOptional.isEmpty()) {
            return BaseResponse.build(HttpStatus.BAD_REQUEST, KEY_NOT_FOUND, null);
        }

        menuRepository.deleteById(id);
        return BaseResponse.build(HttpStatus.OK,KEY_FOUND,null);
    }
}
