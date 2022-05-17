package com.alterra.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Objects;
import java.util.Optional;

import com.alterra.demo.domain.common.ApiResponse;
import com.alterra.demo.domain.dao.*;
import com.alterra.demo.domain.dto.*;
import com.alterra.demo.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = RestaurantService.class)
public class RestaurantServiceTest {

        @MockBean
        private CityRepository cityRepository;

        @MockBean
        private RestaurantRepository restaurantRepository;

        @MockBean
        private UsersRepository usersRepository;

        @Autowired
        private RestaurantService restaurantService;

        @Test
        public void createRestaurant() {
                CityDao cityDao = CityDao.builder()
                                .id(1L)
                                .cityName("Surabaya")
                                .build();

                UsersDao usersDao = UsersDao.builder()
                                .id(1L)
                                .name("Laz")
                                .build();

                RestaurantDao restaurantDao = RestaurantDao.builder()
                                .id(1L)
                                .restaurantName("McDonald")
                                .build();
                RestaurantDto restaurantDto = RestaurantDto.builder()
                                .id(1L)
                                .city(CityDto.builder().id(1L).build())
                                .restaurantName("McDonald")
                                .ownerId(UsersDto.builder().id(1L).build())
                                .build();

                when(cityRepository.findById(anyLong())).thenReturn(Optional.of(cityDao));
                when(usersRepository.findById(anyLong())).thenReturn(Optional.of(usersDao));
                when(restaurantRepository.save(any())).thenReturn(restaurantDao);
                ResponseEntity<Object> responseEntity = restaurantService.createNewRestaurant(RestaurantDto.builder()
                                .city(CityDto.builder()
                                                .id(1L)
                                                .build())
                                .restaurantName("McDonald")
                                .ownerId(UsersDto.builder()
                                                .id(1L)
                                                .build())
                                .build());
                ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
                RestaurantDto data = (RestaurantDto) Objects.requireNonNull(apiResponse).getData();
                assertEquals(1L, data.getId());
                assertEquals(restaurantDto.getCity().getId(), data.getCity().getId());
                assertEquals("McDonald", data.getRestaurantName());
                assertEquals(restaurantDto.getOwnerId().getId(), data.getOwnerId().getId());
        }

        @Test
        public void updateRestaurant_Success() throws Exception {
                CityDao cityDao = CityDao.builder()
                                .id(1L)
                                .cityName("Surabaya")
                                .build();

                UsersDao usersDao = UsersDao.builder()
                                .id(1L)
                                .name("Laz")
                                .build();

                RestaurantDao restaurantDao = RestaurantDao.builder()
                                .id(1L)
                                .restaurantName("McDonald")
                                .build();
                RestaurantDto restaurantDto = RestaurantDto.builder()
                                .id(1L)
                                .city(CityDto.builder().id(1L).build())
                                .restaurantName("McDonald")
                                .ownerId(UsersDto.builder().id(1L).build())
                                .build();

                when(cityRepository.findById(anyLong())).thenReturn(Optional.of(cityDao));
                when(usersRepository.findById(anyLong())).thenReturn(Optional.of(usersDao));
                when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantDao));
                when(restaurantRepository.save(any())).thenReturn(restaurantDao);
                ResponseEntity<Object> responseEntity = restaurantService.updateRestaurant(anyLong(),
                                RestaurantDto.builder()
                                                .city(CityDto.builder()
                                                                .id(1L)
                                                                .build())
                                                .restaurantName("Burger King")
                                                .ownerId(UsersDto.builder()
                                                                .id(1L)
                                                                .build())
                                                .build());
                ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
                RestaurantDto data = (RestaurantDto) Objects.requireNonNull(apiResponse).getData();
                assertEquals(1L, data.getId());
                assertEquals(restaurantDto.getCity().getId(), data.getCity().getId());
                assertEquals("Burger King", data.getRestaurantName());
                assertEquals(restaurantDto.getOwnerId().getId(), data.getOwnerId().getId());
        }

        @Test
        public void updateRestaurant_Failed() {
                when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
                ResponseEntity<Object> responseEntity = restaurantService.updateRestaurant(anyLong(),
                                RestaurantDto.builder().build());

                assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        }

        // @Test
        // public void getAllRestaurant(){
        // CityDao cityDao = CityDao.builder()
        // .id(1L)
        // .cityName("Surabaya")
        // .build();
        //
        // UsersDao usersDao = UsersDao.builder()
        // .id(1L)
        // .name("Laz")
        // .build();
        //
        // RestaurantDao restaurantDao = RestaurantDao.builder()
        // .id(1L)
        // .city(cityDao)
        // .restaurantName("McDonald")
        // .ownerId(usersDao)
        // .build();
        // RestaurantDto restaurantDto = RestaurantDto.builder()
        // .id(1L)
        // .city(CityDto.builder().id(1L).build())
        // .restaurantName("McDonald")
        // .ownerId(UsersDto.builder().id(1L).build())
        // .build();
        //
        // when(usersRepository.findById(anyLong())).thenReturn(Optional.of(usersDao));
        // when(cityRepository.findById(anyLong())).thenReturn(Optional.of(cityDao));
        // when(restaurantRepository.findAll()).thenReturn(List.of(restaurantDao));
        // ResponseEntity<Object> responseEntity = restaurantService.getAllRestaurant();
        // ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        // List<RestaurantDto> restaurantDtoList = (List<RestaurantDto>)
        // apiResponse.getData();
        //
        // assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        // assertEquals(KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        // assertEquals(1,restaurantDtoList.size());
        // }

        @Test
        public void getRestaurantById_Failed() {
                when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
                ResponseEntity<Object> responseEntity = restaurantService.getRestaurantById(anyLong());

                assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        }

        @Test
        public void getRestaurantById_Success() {
                CityDao cityDao = CityDao.builder()
                                .id(1L)
                                .cityName("Surabaya")
                                .build();

                UsersDao user = UsersDao.builder()
                                .id(1L)
                                .name("Laz")
                                .build();

                RestaurantDao restaurantDao = RestaurantDao.builder()
                                .id(1L)
                                .city(cityDao)
                                .restaurantName("McDonald")
                                .ownerId(user)
                                .build();
                when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantDao));
                ResponseEntity<Object> responseEntity = restaurantService.getRestaurantById(anyLong());

                assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        }

        @Test
        public void deleteRestaurant_Failed() {
                when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
                ResponseEntity<Object> user = restaurantService.deleteRestaurant(anyLong());

                assertEquals(HttpStatus.BAD_REQUEST.value(), user.getStatusCodeValue());
        }

        @Test
        public void deleteCity_Success() {
                CityDao cityDao = CityDao.builder()
                                .id(1L)
                                .cityName("Surabaya")
                                .build();

                UsersDao user = UsersDao.builder()
                                .id(1L)
                                .name("Laz")
                                .build();

                when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(RestaurantDao.builder()
                                .id(1L)
                                .city(cityDao)
                                .restaurantName("McDonald")
                                .ownerId(user)
                                .build()));
                ResponseEntity<Object> restaurant = restaurantService.deleteRestaurant(anyLong());

                assertEquals(HttpStatus.OK.value(), restaurant.getStatusCodeValue());
        }
}