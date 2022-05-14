package com.alterra.demo.controller;
import com.alterra.demo.domain.model.UsernamePassword;
import com.alterra.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UsernamePassword req){
        authService.register(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/info")
    public ResponseEntity<?> generateUsername(HttpServletRequest request){
        return ResponseEntity.ok(authService.generateUsername(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> generateToken(@RequestBody UsernamePassword req){
        return ResponseEntity.ok(authService.generateToken(req));
    }
}
