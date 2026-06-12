package com.gdb.models;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String username;
    private String loginId;
    private String password;
    private LocalDateTime createdDate;
    private boolean isActive;

    public User(String username, String loginId, String password) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.createdDate = LocalDateTime.now();
        this.isActive = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}