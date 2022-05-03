package com.alterra.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse {
    private BaseResponse(){}

    public static ResponseEntity<Object> build(HttpStatus httpStatus, String code, Object data){
        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("data",data);
        return new ResponseEntity<>(map,httpStatus);
    }
}
