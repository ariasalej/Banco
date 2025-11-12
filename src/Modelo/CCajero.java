/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alejo
 */
public class CCajero {

    public int id;
    public Map<Integer, Integer> billetes;
    public double saldoTotal;
    public int estado; // Mismo sistema que clientes

    public CCajero() {
        billetes = new HashMap<>();
        // Valores por defecto (puedes cambiar según tu tabla)
        billetes.put(10000, 100);
        billetes.put(20000, 100);
        billetes.put(50000, 100);
        billetes.put(100000, 100);
        calcularSaldoTotal();
    }

    public CCajero(int id, Map<Integer, Integer> billetes, int estado) {
        this.id = id;
        this.billetes = billetes;
        this.estado = estado;
        calcularSaldoTotal();
    }

    public void calcularSaldoTotal() {
        saldoTotal = 0;
        for (Map.Entry<Integer, Integer> entry : billetes.entrySet()) {
            saldoTotal += entry.getKey() * entry.getValue();
        }
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public Map<Integer, Integer> getBilletes() {
        return billetes;
    }

    public void setBilletes(Map<Integer, Integer> billetes) {
        this.billetes = billetes;
        calcularSaldoTotal();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
