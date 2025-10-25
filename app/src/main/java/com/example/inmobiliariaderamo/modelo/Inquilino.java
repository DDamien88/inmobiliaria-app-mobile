package com.example.inmobiliariaderamo.modelo;

import java.io.Serializable;

public class Inquilino implements Serializable {

    private int idInquilino;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String garante;
    private String telGarante;

    public Inquilino() {

    }

    public Inquilino(int idInquilino, String nombre, String apellido, String dni, String telefono, String email, String garante, String telGarante) {
        this.idInquilino = idInquilino;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.garante = garante;
        this.telGarante = telGarante;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGarante() {
        return garante;
    }

    public void setGarante(String garante) {
        this.garante = garante;
    }

    public String getTelGarante() {
        return telGarante;
    }

    public void setTelGarante(String telGarante) {
        this.telGarante = telGarante;
    }
}