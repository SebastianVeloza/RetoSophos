package com.example.retosohphos.models;

public class UsuarioResponse {
    private int id;
    private String nombre;
    private String apellido;
    private boolean acceso;
    private boolean admin;

    public UsuarioResponse() {
    }

    public UsuarioResponse(boolean acceso) {
        this.acceso = acceso;
    }

    public UsuarioResponse(int id, String nombre, String apellido, boolean acceso, boolean admin) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.acceso = acceso;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isAcceso() {
        return acceso;
    }

    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
