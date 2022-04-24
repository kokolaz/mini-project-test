package com.alterra.demo.controller;

import com.alterra.demo.service.MainService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/")
    public ResponseEntity<Object> hello() {
        return mainService.main();
    }

}
