package com.alterra.demo.controller;

import com.alterra.demo.domain.dto.UsersDto;
import com.alterra.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Object> get() {
        return userService.getAllUser();
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody UsersDto user) {
        return userService.createNewUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UsersDto user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
