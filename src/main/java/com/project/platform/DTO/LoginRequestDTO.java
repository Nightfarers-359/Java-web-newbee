package com.project.platform.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {
    
    @NotEmpty(message = "账号（邮箱/用户名）不能为空")
    private String emailorname;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 30, message = "密码长度不正确")
    private String password;

    public String getEmailorname() { return emailorname; }
    public void setEmailorname(String emailorname) { this.emailorname = emailorname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}