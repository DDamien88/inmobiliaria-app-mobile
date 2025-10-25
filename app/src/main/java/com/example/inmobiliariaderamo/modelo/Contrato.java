package com.example.inmobiliariaderamo.modelo;

import java.io.Serializable;
import java.util.Date;

public class Contrato implements Serializable {

    private int idContrato;
    private int idInquilino;
    private int idInmueble;
    private Date fechaInicio;
    private Date fechaFin;
    private double monto;
    private Inquilino inquilino;
    private Inmueble inmueble;

    public Contrato() {
    }

    public Contrato(int idContrato, int idInquilino, int idInmueble, Date fechaInicio, Date fechaFin, double monto, Inquilino inquilino, Inmueble inmueble) {
        this.idContrato = idContrato;
        this.idInquilino = idInquilino;
        this.idInmueble = idInmueble;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.monto = monto;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }
}