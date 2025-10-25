package com.example.inmobiliariaderamo.modelo;

import java.io.Serializable;
import java.util.Date;

public class Pago implements Serializable {

    private int idPago;
    private String detalle;
    private Date fechaPago;
    private double monto;
    private boolean estado;
    private Contrato contrato;
    private int idContrato;


    public Pago() {
    }

    public Pago(int idPago, String detalle, Date fechaPago, double monto, boolean estado, Contrato contrato, int idContrato) {
        this.idPago = idPago;
        this.detalle = detalle;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.estado = estado;
        this.contrato = contrato;
        this.idContrato = idContrato;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }
}