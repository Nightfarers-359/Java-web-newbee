package com.project.platform.DTO;

import lombok.Data;

@Data
public class JWTpayload {
    private int id;
    private String username;
    private boolean admin;
}
