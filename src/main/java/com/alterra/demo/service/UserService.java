package com.alterra.demo.service;

import com.alterra.demo.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                    .build());
        }

        return ResponseUtil.build(KEY_FOUND, usersDtoList, HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserById(Long id){
        Optional<UsersDao> usersDaoOptional = usersRepository.findById(id);
        if (usersDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null ,HttpStatus.BAD_REQUEST);
        }
        UsersDao usersDao = usersDaoOptional.get();

        return ResponseUtil.build(KEY_FOUND, UsersDto.builder()
                .id(usersDao.getId())
                .name(usersDao.getName())
                .city(CityDto.builder()
                        .id(usersDao.getCity().getId())
                        .cityName(usersDao.getCity().getCityName())
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> createNewUser(UsersDto user){
        Optional<CityDao> cityDao = cityRepository.findById(user.getCity().getId());
        if (cityDao.isEmpty())
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);

        UsersDao usersDao = UsersDao
                .builder()
                .name(user.getName())
                .city(cityDao.get())
                .build();

        usersDao = usersRepository.save(usersDao);
        return ResponseUtil.build(KEY_FOUND, UsersDto.builder()
                .id(usersDao.getId())
                .name(usersDao.getName())
                .city(
                        CityDto
                                .builder()
                                .id(cityDao.get().getId())
                                .cityName(cityDao.get().getCityName())
                                .build()
                )
                .build(), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateUser(Long id,UsersDto update){
        Optional<UsersDao> UserDaoOptional = usersRepository.findById(id);
        if (UserDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        }

        Optional<CityDao> cityDaoOptional = cityRepository.findById(update.getCity().getId());
        if (cityDaoOptional.isEmpty()){
            return ResponseUtil.build(KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        }

        UsersDao usersDao = UserDaoOptional.get();
        usersDao.setName(update.getName());
        usersDao.setCity(cityDaoOptional.get());
        usersDao = usersRepository.save(usersDao);

        return ResponseUtil.build(KEY_FOUND,UsersDto.builder()
                .id(usersDao.getId())
                .name(usersDao.getName())
                .city(CityDto.builder()
                        .id(usersDao.getCity().getId())
                        .cityName(usersDao.getCity().getCityName())
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteUser(Long id){
        Optional<UsersDao> userDaoOptional = usersRepository.findById(id);

        if (userDaoOptional.isEmpty()) {
            return ResponseUtil.build(KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }

        usersRepository.deleteById(id);
        return ResponseUtil.build(KEY_FOUND,null, HttpStatus.OK);
    }
}
