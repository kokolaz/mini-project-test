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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private CityRepository cityRepository;

    @Test
    public void createUser() {
        CityDao cityDao = CityDao.builder()
                .id(1L)
                .cityName("Surabaya")
                .build();

        UsersDao usersDao = UsersDao.builder()
                .id(1L)
                .name("Laz")
                .build();
        UsersDto usersDto = UsersDto.builder()
                .id(1L)
                .name("Laz")
                .city(CityDto.builder().id(1L).build())
                .build();

        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(cityDao));
        when(usersRepository.save(any())).thenReturn(usersDao);
        ResponseEntity<Object> responseEntity = userService.createNewUser(UsersDto.builder()
                .name("Laz")
                .city(CityDto.builder()
                        .id(1L)
                        .build())
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UsersDto data = (UsersDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("Laz",data.getName());
        assertEquals(usersDto.getCity().getId(),data.getCity().getId());
    }

    @Test
    public void updateUser_Success() throws Exception {
        CityDao cityDao = CityDao.builder()
                .id(1L)
                .cityName("Surabaya")
                .build();

        UsersDao usersDao = UsersDao.builder()
                .id(1L)
                .name("Laz")
                .city(cityDao)
                .build();
        UsersDto usersDto = UsersDto.builder()
                .id(1L)
                .name("Laz")
                .city(CityDto.builder().id(1L).build())
                .build();

        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(cityDao));
        when(usersRepository.findById(anyLong())).thenReturn(Optional.of(usersDao));
        when(usersRepository.save(any())).thenReturn(usersDao);
        ResponseEntity<Object> responseEntity = userService.updateUser(anyLong(), UsersDto.builder()
                .name("Ron")
                .city(CityDto.builder()
                        .id(1L)
                        .build())
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UsersDto data = (UsersDto) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("Ron",data.getName());
        assertEquals(usersDto.getCity().getId(),data.getId());
    }

    @Test
    public void updateUser_Failed(){
        when(usersRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = userService.updateUser(anyLong(),UsersDto.builder().build());

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void getAllUser(){
        CityDao cityDao = CityDao.builder()
                .id(1L)
                .cityName("Surabaya")
                .build();

        UsersDao usersDao = UsersDao.builder()
                .id(1L)
                .name("Laz")
                .city(cityDao)
                .build();
        UsersDto usersDto = UsersDto.builder()
                .id(1L)
                .name("Laz")
                .city(CityDto.builder().id(1L).build())
                .build();

        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(cityDao));
        when(usersRepository.findAll()).thenReturn(List.of(usersDao));
        ResponseEntity<Object> responseEntity = userService.getAllUser();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<UsersDto> usersDtoList = (List<UsersDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,usersDtoList.size());
    }

    @Test
    public void getUserById_Failed(){
        when(usersRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = userService.getUserById(anyLong());

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void getCityById_Success(){
        CityDao cityDao = CityDao.builder()
                .id(1L)
                .cityName("Surabaya")
                .build();

        UsersDao user = UsersDao.builder()
                .id(1L)
                .name("Laz")
                .city(cityDao)
                .build();
        when(usersRepository.findById(anyLong())).thenReturn(Optional.of(user));
        ResponseEntity<Object> responseEntity = userService.getUserById(anyLong());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void deleteUser_Failed(){
        when(usersRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> user = userService.deleteUser(anyLong());

        assertEquals(HttpStatus.BAD_REQUEST.value(),user.getStatusCodeValue());
    }

    @Test
    public void deleteCity_Success(){
        CityDao cityDao = CityDao.builder()
                .id(1L)
                .cityName("Surabaya")
                .build();

        when(usersRepository.findById(anyLong())).thenReturn(Optional.of(UsersDao.builder()
                .id(1L)
                .name("Laz")
                .city(cityDao)
                .build()));
        ResponseEntity<Object> user = userService.deleteUser(anyLong());

        assertEquals(HttpStatus.OK.value(),user.getStatusCodeValue());
    }
}