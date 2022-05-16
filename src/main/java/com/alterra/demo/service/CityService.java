package com.alterra.demo.service;

import static com.alterra.demo.constant.ConstantApp.*;

import java.util.*;

import com.alterra.demo.domain.dao.CityDao;
import com.alterra.demo.domain.dto.CityDto;
import com.alterra.demo.repository.CityRepository;
import com.alterra.demo.util.ResponseUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<CityDao> findAll() {
        return cityRepository.findAll();
    }

    public ResponseEntity<Object> getAllCity() {
        List<CityDao> cityDaoList = cityRepository.findAll();
        List<CityDto> cityDtoList = new ArrayList<>();

        for (CityDao cityDao : cityDaoList) {
            cityDtoList.add(CityDto.builder()
                    .id(cityDao.getId())
                    .cityName(cityDao.getCityName())
                    .build());
        }

        return ResponseUtil.build(KEY_FOUND, cityDtoList, HttpStatus.OK);
    }

    public ResponseEntity<Object> getCityById(Long id) {
        Optional<CityDao> cityDaoOptional = cityRepository.findById(id);
        if (cityDaoOptional.isEmpty()) {
            return ResponseUtil.build(KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }
        CityDao cityDao = cityDaoOptional.get();

        return ResponseUtil.build(KEY_FOUND, CityDto.builder()
                .id(cityDao.getId())
                .cityName(cityDao.getCityName())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> createNewCity(CityDto city) {
        CityDao cityDao = CityDao
                .builder()
                .cityName(city.getCityName())
                .build();

        cityDao = cityRepository.save(cityDao);
        return ResponseUtil.build(KEY_FOUND, CityDto.builder()
                .id(cityDao.getId())
                .cityName(cityDao.getCityName())
                .build(), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateCity(Long id, CityDto update) {
        Optional<CityDao> cityDaoOptional = cityRepository.findById(id);
        if (cityDaoOptional.isEmpty()) {
            return ResponseUtil.build(KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }

        CityDao cityDao = cityDaoOptional.get();
        cityDao.setCityName(update.getCityName());
        cityDao = cityRepository.save(cityDao);

        return ResponseUtil.build(KEY_FOUND, CityDto.builder()
                .id(cityDao.getId())
                .cityName(cityDao.getCityName())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteCity(Long id) {
        Optional<CityDao> cityDaoOptional = cityRepository.findById(id);

        if (cityDaoOptional.isEmpty()) {
            return ResponseUtil.build(KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }

        cityRepository.deleteById(id);
        return ResponseUtil.build(KEY_FOUND, null, HttpStatus.OK);
    }
}