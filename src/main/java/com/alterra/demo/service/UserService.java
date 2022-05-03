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
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CityRepository cityRepository;

    public ResponseEntity<Object> getAllUser(){
        List<UsersDao> usersDaoList = usersRepository.findAll();
        List<UsersDto> usersDtoList = new ArrayList<>();

        for(UsersDao usersDao: usersDaoList){
            Optional<CityDao> cityDaoOptional = cityRepository.findById(usersDao.getCity().getId());
            usersDtoList.add(UsersDto.builder()
                    .id(usersDao.getId())
                    .name(usersDao.getName())
                    .city(CityDto.builder()
                            .id(cityDaoOptional.get().getId())
                            .cityName(cityDaoOptional.get().getCityName())
                        .build())
                    .isAdmin(usersDao.getIsAdmin())
                    .build());
        }

        return BaseResponse.build(HttpStatus.OK, KEY_FOUND, usersDtoList);
    }

    public ResponseEntity<Object> createNewUser(UsersDto user){
        Optional<CityDao> cityDao = cityRepository.findById(user.getCity().getId());
        if (cityDao.isEmpty())
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);

        UsersDao usersDao = UsersDao
                .builder()
                .name(user.getName())
                .city(cityDao.get())
                .isAdmin(user.getIsAdmin())
                .build();

        usersRepository.save(usersDao);
        return BaseResponse.build(HttpStatus.CREATED, KEY_FOUND, UsersDto.builder()
                .id(usersDao.getId())
                .name(usersDao.getName())
                .city(
                        CityDto
                                .builder()
                                .id(cityDao.get().getId())
                                .cityName(cityDao.get().getCityName())
                                .build()
                )
                .isAdmin(usersDao.getIsAdmin())
                .build());
    }

    public ResponseEntity<Object> updateUser(Long id,UsersDto update){
        Optional<UsersDao> UserDaoOptional = usersRepository.findById(id);
        if (UserDaoOptional.isEmpty()){
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);
        }

        Optional<CityDao> cityDaoOptional = cityRepository.findById(update.getCity().getId());
        if (cityDaoOptional.isEmpty()){
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);
        }

        UsersDao usersDao = UserDaoOptional.get();
        usersDao.setName(update.getName());
        usersDao.setCity(cityDaoOptional.get());
        usersDao.setIsAdmin(update.getIsAdmin());
        usersRepository.save(usersDao);

        return BaseResponse.build(HttpStatus.OK,KEY_FOUND,UsersDto.builder()
                .id(usersDao.getId())
                .name(usersDao.getName())
                .city(CityDto.builder()
                        .id(usersDao.getCity().getId())
                        .cityName(usersDao.getCity().getCityName())
                        .build())
                .isAdmin(usersDao.getIsAdmin())
                .build());
    }

    public ResponseEntity<Object> deleteUser(Long id){
        Optional<UsersDao> userDaoOptional = usersRepository.findById(id);

        if (userDaoOptional.isEmpty()) {
            return BaseResponse.build(HttpStatus.BAD_REQUEST, KEY_NOT_FOUND, null);
        }

        usersRepository.deleteById(id);
        return BaseResponse.build(HttpStatus.OK,KEY_FOUND,null);
    }
}
