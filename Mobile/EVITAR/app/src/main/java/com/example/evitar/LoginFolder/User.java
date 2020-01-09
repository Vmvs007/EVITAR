package com.example.evitar.LoginFolder;

public class User {
    private int id;
    private Long idColaborador;
    private String username;
    private String token;

    public User(int id, Long idColaborador, String username, String token) {
        this.id = id;
        this.idColaborador = idColaborador;
        this.username = username;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public Long getIdColaborador() {
        return idColaborador;
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

    public void setIdColaborador(Long idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
