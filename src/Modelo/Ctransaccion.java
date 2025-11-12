/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDateTime;

/**
 *
 * @author Alejo
 */
public class Ctransaccion {
    public int id;
    public String tipo; // "retiro y consignacion"
    public double monto;
    public LocalDateTime fecha;
    public int idCliente;
    public int idCajero;

    public Ctransaccion(int id, String tipo, double monto, int idCliente, int idCajero) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.idCliente = idCliente;
        this.idCajero = idCajero;
        this.fecha = LocalDateTime.now();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdCajero() {
        return idCajero;
    }
}
