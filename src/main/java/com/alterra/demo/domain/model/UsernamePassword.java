package com.alterra.demo.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UsernamePassword {
    private String username;
    private String password;
}
