package com.example.evitar;

public class User {
    private int id;
    private String username;
    private String token;

    public User(Integer id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
