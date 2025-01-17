package com.project.auth.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

public class User {
    private String username;

    private String userpassword;
    
    public User(String username, String userpassword) {
        this.username = username;
        this.userpassword = userpassword;
    }
    public User(){

    }


    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
