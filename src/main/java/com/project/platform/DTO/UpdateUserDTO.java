package com.project.platform.DTO;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String username;  
    private String nickname; 
    private String email;     
    private String phone;
}
