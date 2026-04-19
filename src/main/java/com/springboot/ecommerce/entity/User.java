package com.springboot.ecommerce.entity;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private String password;
    private String role;//seller or buyer
    private LocalDateTime create_at;

    public User(){}
    public User(String username, String password, String role, LocalDateTime create_at) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.create_at = create_at;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public LocalDateTime getCreate_at() {
        return create_at;
    }
    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }
}
