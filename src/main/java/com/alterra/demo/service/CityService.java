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
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public ResponseEntity<Object> getAllCity(){
        List<CityDao> cityDaoList = cityRepository.findAll();
        List<CityDto> cityDtoList = new ArrayList<>();

        for(CityDao cityDao: cityDaoList){
            cityDtoList.add(CityDto.builder()
                    .id(cityDao.getId())
                    .cityName(cityDao.getCityName())
                    .build());
        }

        return BaseResponse.build(HttpStatus.OK, KEY_FOUND, cityDtoList);
    }

    public ResponseEntity<Object> createNewCity(CityDto city){
        CityDao cityDao = CityDao
                .builder()
                .cityName(city.getCityName())
                .build();

        cityRepository.save(cityDao);
        return BaseResponse.build(HttpStatus.CREATED, KEY_FOUND, CityDto.builder()
                .id(cityDao.getId())
                .cityName(cityDao.getCityName())
                .build());
    }

    public ResponseEntity<Object> updateCity(Long id,CityDto update){
        Optional<CityDao> cityDaoOptional = cityRepository.findById(id);
        if (cityDaoOptional.isEmpty()){
            return BaseResponse.build(HttpStatus.BAD_REQUEST,KEY_NOT_FOUND,null);
        }

        CityDao cityDao = cityDaoOptional.get();
        cityDao.setCityName(update.getCityName());
        cityRepository.save(cityDao);

        return BaseResponse.build(HttpStatus.OK,KEY_FOUND,CityDto.builder()
                .id(cityDao.getId())
                .cityName(cityDao.getCityName())
                .build());
    }

    public ResponseEntity<Object> deleteCity(Long id){
        Optional<CityDao> cityDaoOptional = cityRepository.findById(id);

        if (cityDaoOptional.isEmpty()) {
            return BaseResponse.build(HttpStatus.BAD_REQUEST, KEY_NOT_FOUND, null);
        }

        cityRepository.deleteById(id);
        return BaseResponse.build(HttpStatus.OK,KEY_FOUND,null);
    }
}
