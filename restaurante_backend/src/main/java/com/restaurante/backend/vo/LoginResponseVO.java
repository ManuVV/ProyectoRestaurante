package com.restaurante.backend.vo;

public class LoginResponseVO {

    private String token;
    private String nombreCompleto;
    private String email;
    private String rol;

    public LoginResponseVO() {}

    public LoginResponseVO(String token, String nombreCompleto, String email, String rol) {
        this.token = token;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.rol = rol;
    }

    // Getters y setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}