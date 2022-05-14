package com.alterra.demo.controller;

import com.alterra.demo.domain.dto.RestaurantDto;
import com.alterra.demo.domain.dto.UsersDto;
import com.alterra.demo.service.RestaurantService;

import com.alterra.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

//    @GetMapping("/")
//    public ResponseEntity<Object> get() {
//        return restaurantService.getAllRestaurant();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody RestaurantDto restaurant) {
        return restaurantService.createNewRestaurant(restaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody RestaurantDto restaurant) {
        return restaurantService.updateRestaurant(id, restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return restaurantService.deleteRestaurant(id);
    }
}
