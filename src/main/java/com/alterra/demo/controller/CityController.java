package com.alterra.demo.controller;

import com.alterra.demo.domain.dto.CityDto;
import com.alterra.demo.service.CityService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/")
    public ResponseEntity<Object> get() {
        return cityService.getAllCity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody CityDto city) {
        return cityService.createNewCity(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody CityDto city) {
        return cityService.updateCity(id, city);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return cityService.deleteCity(id);
    }
}
