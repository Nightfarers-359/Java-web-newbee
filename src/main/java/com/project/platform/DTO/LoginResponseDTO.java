package com.project.platform.DTO;

public class LoginResponseDTO {
    private String token;
    private UserResponseDTO userInfo; // 这里嵌套使用 UserResponseDTO

    public LoginResponseDTO(String token, UserResponseDTO userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }
    
    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return token;
    }
    public void setUserInfo(UserResponseDTO userInfo){
        this.userInfo = userInfo;
    }
    public UserResponseDTO getResponseDTO(){
        return userInfo;
    }
    
}