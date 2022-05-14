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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.alterra.demo.constant.ConstantApp.KEY_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CityService.class)
public class CityServiceTest {

    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

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
        assertEquals(1L,data.getId());
        assertEquals("Jakarta",data.getCityName());
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
        assertEquals(1L,data.getId());
        assertEquals("Surabaya",data.getCityName());
    }

    @Test
    public void updateCity_Failed(){
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = cityService.updateCity(anyLong(),CityDto.builder().build());

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void getAllCity(){
        CityDao city = CityDao.builder()
                .id(1L)
                .cityName("Malang")
                .build();
        when(cityRepository.findAll()).thenReturn(List.of(city));
        ResponseEntity<Object> responseEntity = cityService.getAllCity();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<CityDto> cityDtoList = (List<CityDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,cityDtoList.size());
    }

    @Test
    public void getCityById_Failed(){
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = cityService.getCityById(anyLong());

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void getCityById_Success(){
        CityDao city = CityDao.builder()
                .id(1L)
                .cityName("Malang")
                .build();
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));
        ResponseEntity<Object> responseEntity = cityService.getCityById(anyLong());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void deleteCity_Failed(){
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> city = cityService.deleteCity(anyLong());

        assertEquals(HttpStatus.BAD_REQUEST.value(),city.getStatusCodeValue());
    }

    @Test
    public void deleteCity_Success(){
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(CityDao.builder()
                .id(1L)
                .cityName("Jakarta")
                .build()));
        ResponseEntity<Object> city = cityService.deleteCity(anyLong());

        assertEquals(HttpStatus.OK.value(),city.getStatusCodeValue());
    }
}