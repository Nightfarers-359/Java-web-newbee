package com.project.platform.entity;

public class BaseClass {
    //用户注册数据
    public abstract class UserData {
        public String name;
        public String password;
        public String email;
        public String phone;
        public String role;
    }
    //用户信息
    public abstract class UserInfo {
        public String name;
        public String email;
        public String phone;
        public String role;
    }
    //用户登录数据
    public abstract class LoginData {
        public String emailorname;
        public String password;
    }
}
