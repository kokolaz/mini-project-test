package com.alterra.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.alterra.demo.domain.common.ApiResponse;
import com.alterra.demo.domain.dao.CityDao;
import com.alterra.demo.domain.dto.CityDto;
import com.alterra.demo.repository.CityRepository;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@Tag(value = "CityServiceTest")
public class CityServiceTest {

    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    private final CityDao city = CityDao.builder()
            .id(1L)
            .cityName("Malang")
            .build();

    @Test
    public void createCity() throws Exception {
        CityDao cityDao = CityDao.builder()
                .id(1L)
                .cityName("Jakarta")
                .build();
        CityDto cityDto = CityDto.builder()
                .id(1L)
                .cityName("Jakarta")
                .build();

        when(cityRepository.save(any())).thenReturn(cityDao);
        ResponseEntity<Object> responseEntity = cityService.createNewCity(CityDto.builder()
                .cityName("Jakarta")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        CityDto data = (CityDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L, data.getId());
        assertEquals("Jakarta", data.getCityName());
    }

    @Test
    public void updateCity_Success() throws Exception {
        CityDao cityDao = CityDao.builder()
                .id(1L)
                .cityName("Jakarta")
                .build();
        CityDto cityDto = CityDto.builder()
                .id(1L)
                .cityName("Jakarta")
                .build();

        when(cityRepository.findById(1L)).thenReturn(Optional.of(cityDao));
        when(cityRepository.save(any())).thenReturn(cityDao);
        ResponseEntity<Object> responseEntity = cityService.updateCity(1L, CityDto.builder()
                .cityName("Surabaya")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        CityDto data = (CityDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L, data.getId());
        assertEquals("Surabaya", data.getCityName());
    }

    @Test
    public void updateCity_Failed() {
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = cityService.updateCity(anyLong(), CityDto.builder().build());

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    public void getAllCity() {

        when(this.cityRepository.findAll()).thenReturn(List.of(city));
        ResponseEntity<Object> responseEntity = cityService.getAllCity();
        ApiResponse<List<CityDto>> apiResponse = (ApiResponse<List<CityDto>>) responseEntity.getBody();
        List<CityDto> cityDtoList = apiResponse.getData();

        assertNotNull(cityDtoList);
        // assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        // assertEquals(KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        // assertEquals(1, cityDtoList.size());
    }

    @Test
    public void getCityById_Failed() {
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = cityService.getCityById(anyLong());

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    public void getCityById_Success() {
        CityDao city = CityDao.builder()
                .id(1L)
                .cityName("Malang")
                .build();
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));
        ResponseEntity<Object> responseEntity = cityService.getCityById(anyLong());

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    public void deleteCity_Failed() {
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> city = cityService.deleteCity(anyLong());

        assertEquals(HttpStatus.BAD_REQUEST.value(), city.getStatusCodeValue());
    }

    @Test
    public void deleteCity_Success() {
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(CityDao.builder()
                .id(1L)
                .cityName("Jakarta")
                .build()));
        ResponseEntity<Object> city = cityService.deleteCity(anyLong());

        assertEquals(HttpStatus.OK.value(), city.getStatusCodeValue());
    }

}