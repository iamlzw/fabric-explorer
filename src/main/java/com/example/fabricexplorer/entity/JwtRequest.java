package com.example.fabricexplorer.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    private String username;
    private String password;
    //need default constructor for JSON Parsing
    public JwtRequest()
    {
    }
    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}
