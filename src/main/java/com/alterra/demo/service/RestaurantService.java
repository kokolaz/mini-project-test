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
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CityRepository cityRepository;

//    public ResponseEntity<Object> getAllRestaurant(){
//        List<RestaurantDao> restaurantDaoList = restaurantRepository.findAll();
//        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
//
//        for(RestaurantDao restaurantDao: restaurantDaoList){
//            Optional<CityDao> cityDaoOptional = cityRepository.findById(restaurantDao.getCity().getId());
//            Optional<UsersDao> usersDaoOptional = usersRepository.findById(restaurantDao.getOwnerId().getId());
//            restaurantDtoList.add(RestaurantDto.builder()
//                    .id(restaurantDao.getId())
//                    .city(CityDto.builder()
//                            .id(cityDaoOptional.get().getId())
//                            .cityName(cityDaoOptional.get().getCityName())
//                            .build())
//                    .restaurantName(restaurantDao.getRestaurantName())
//                    .ownerId(UsersDto.builder()
//                            .id(usersDaoOptional.get().getId())
//                            .name(usersDaoOptional.get().getName())
//                            .build())
//                    .build());
//        }
//
//        return ResponseUtil.build(KEY_FOUND, restaurantDtoList, HttpStatus.OK);
//    }

    public ResponseEntity<Object> getRestaurantById(Long id){
        Optional<RestaurantDao> restaurantDaoOptional = restaurantRepository.findById(id);
        if (restaurantDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null ,HttpStatus.BAD_REQUEST);
        }
        RestaurantDao restaurantDao = restaurantDaoOptional.get();

        return ResponseUtil.build(KEY_FOUND, RestaurantDto.builder()
                .id(restaurantDao.getId())
                .city(CityDto.builder()
                        .id(restaurantDao.getCity().getId())
                        .cityName(restaurantDao.getCity().getCityName())
                        .build())
                .restaurantName(restaurantDao.getRestaurantName())
                .ownerId(UsersDto.builder()
                        .id(restaurantDao.getOwnerId().getId())
                        .name(restaurantDao.getOwnerId().getName())
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> createNewRestaurant(RestaurantDto restaurant){
        Optional<CityDao> cityDao = cityRepository.findById(restaurant.getCity().getId());
        if (cityDao.isEmpty())
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);

        Optional<UsersDao> usersDao = usersRepository.findById(restaurant.getOwnerId().getId());
        if (usersDao.isEmpty())
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);

        RestaurantDao restaurantDao = RestaurantDao
                .builder()
                .restaurantName(restaurant.getRestaurantName())
                .city(cityDao.get())
                .ownerId(usersDao.get())
                .build();

        restaurantDao = restaurantRepository.save(restaurantDao);
        return ResponseUtil.build(KEY_FOUND, RestaurantDto.builder()
                .id(restaurantDao.getId())
                .restaurantName(restaurantDao.getRestaurantName())
                .city(
                        CityDto
                                .builder()
                                .id(cityDao.get().getId())
                                .cityName(cityDao.get().getCityName())
                                .build()
                )
                .ownerId(
                        UsersDto
                                .builder()
                                .id(usersDao.get().getId())
                                .name(usersDao.get().getName())
                                .build()
                )
                .build(), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateRestaurant(Long id,RestaurantDto update){
        Optional<RestaurantDao> restaurantDaoOptional = restaurantRepository.findById(id);
        if (restaurantDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        }

        Optional<UsersDao> UserDaoOptional = usersRepository.findById(update.getOwnerId().getId());
        if (UserDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        }

        Optional<CityDao> cityDaoOptional = cityRepository.findById(update.getCity().getId());
        if (cityDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        }

        RestaurantDao restaurantDao = restaurantDaoOptional.get();
        restaurantDao.setRestaurantName(update.getRestaurantName());
        restaurantDao.setCity(cityDaoOptional.get());
        restaurantDao.setOwnerId(UserDaoOptional.get());
        restaurantRepository.save(restaurantDao);

        return ResponseUtil.build(KEY_FOUND,RestaurantDto.builder()
                .id(restaurantDao.getId())
                .restaurantName(restaurantDao.getRestaurantName())
                .city(CityDto.builder()
                        .id(restaurantDao.getCity().getId())
                        .cityName(restaurantDao.getCity().getCityName())
                        .build())
                .ownerId(UsersDto.builder()
                        .id(restaurantDao.getOwnerId().getId())
                        .name(restaurantDao.getOwnerId().getName())
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteRestaurant(Long id){
        Optional<RestaurantDao> restaurantDaoOptional = restaurantRepository.findById(id);

        if (restaurantDaoOptional.isEmpty()) {
            return ResponseUtil.build(KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }

        restaurantRepository.deleteById(id);
        return ResponseUtil.build(KEY_FOUND,null, HttpStatus.OK);
    }
}
