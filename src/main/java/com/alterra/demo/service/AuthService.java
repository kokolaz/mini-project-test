package com.alterra.demo.service;

import com.alterra.demo.domain.dao.*;
import com.alterra.demo.domain.model.*;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    Admin register(UsernamePassword req);
    TokenResponse generateToken(UsernamePassword req);
    UsernameResponse generateUsername(HttpServletRequest request);
}
