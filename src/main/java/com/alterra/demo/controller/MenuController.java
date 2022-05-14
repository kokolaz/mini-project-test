package com.alterra.demo.controller;
import com.alterra.demo.domain.dto.MenuDto;

import com.alterra.demo.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody MenuDto menu) {
        return menuService.createNewMenu(menu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody MenuDto menu) {
        return menuService.updateMenu(id, menu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return menuService.deleteMenu(id);
    }
}
