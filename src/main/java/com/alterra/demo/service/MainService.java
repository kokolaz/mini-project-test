package com.alterra.demo.service;

import com.alterra.demo.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainService {
    public ResponseEntity<Object> main() {
        return ResponseUtil.build("Hello world by Laz!", null, HttpStatus.OK);
    }

    public ResponseEntity<Object> error(String message) {
        return ResponseUtil.build(message, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
