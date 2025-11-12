/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Alejo
 */
public class CCliente {
    public int id;
    public String nombre;
    public String cuenta;
    public double saldo;
    public int estado; // 0 = disponible, 1 = ocupado

    public CCliente() {
    }

    public CCliente(int id, String nombre, String cuenta, double saldo, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.saldo = saldo;
        this.estado = estado;
    }

    // Getters y setters
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

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}