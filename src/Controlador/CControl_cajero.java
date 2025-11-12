/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cconexion;
import java.sql.*;
import Vista.Interfaz_Cajero1;
import Vista.Interfaz;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Alejo
 */
public class CControl_cajero {

    public Interfaz_Cajero1 vista;
    public HashMap<Integer, Integer> billetes = new HashMap<>();
    public boolean cuentaValidada = false; // Nueva bandera de validación

    public CControl_cajero(Interfaz_Cajero1 vista) {
        this.vista = vista;

        // Billetes iniciales
        billetes.put(10000, 100);
        billetes.put(20000, 100);
        billetes.put(50000, 100);
        billetes.put(100000, 100);

        vista.llenarTabla(billetes);
        inicializarEventos();
    }

    public void inicializarEventos() {
        // Números
        vista.num1.addActionListener(e -> agregarNumero("1"));
        vista.num2.addActionListener(e -> agregarNumero("2"));
        vista.num3.addActionListener(e -> agregarNumero("3"));
        vista.num4.addActionListener(e -> agregarNumero("4"));
        vista.num5.addActionListener(e -> agregarNumero("5"));
        vista.num6.addActionListener(e -> agregarNumero("6"));
        vista.num7.addActionListener(e -> agregarNumero("7"));
        vista.num8.addActionListener(e -> agregarNumero("8"));
        vista.num9.addActionListener(e -> agregarNumero("9"));

        // Botones principales
        vista.Retirar.addActionListener(e -> retirar());
        vista.Borrar.addActionListener(e -> vista.Resultados.setText(""));
        vista.corregir.addActionListener(e -> corregir());
        vista.Salir.addActionListener(e -> {
            vista.dispose(); // cierra solo la ventana del cajero
            Vista.Interfaz menuPrincipal = new Vista.Interfaz(); // crea la ventana principal
            menuPrincipal.setVisible(true); // muestra la ventana principal
        });

        vista.Cancelar.addActionListener(e -> cancelarOperacion());
        vista.Validar.addActionListener(e -> validarCuentaYCajero());
    }


    public void validarCuentaYCajero() {
        String cuenta = vista.Cuenta.getText().trim();
        String numCajero = vista.ID_Cajero.getText().trim();

        if (cuenta.isEmpty() || numCajero.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor digite el número de cuenta y de cajero.");
            return;
        }

        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        if (con == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo conectar a la base de datos.");
            return;
        }

        try {
            // 1️⃣ Verificar si existe el cliente
            String sqlCliente = "SELECT * FROM clientes WHERE cuenta = ?";
            PreparedStatement psCliente = con.prepareStatement(sqlCliente);
            psCliente.setString(1, cuenta);
            ResultSet rsCliente = psCliente.executeQuery();

            if (!rsCliente.next()) {
                JOptionPane.showMessageDialog(vista, " No existe un cliente con esa cuenta.");
                conexion.desconectar(con);
                return;
            }

            String sqlCajero = "SELECT ndiez, nveinte, ncincuenta, ncien, estado FROM cajeros WHERE id = ?";
            PreparedStatement psCajero = con.prepareStatement(sqlCajero);
            psCajero.setString(1, numCajero);
            ResultSet rsCajero = psCajero.executeQuery();

            if (!rsCajero.next()) {
                JOptionPane.showMessageDialog(vista, " No existe un cajero con ese número.");
                conexion.desconectar(con);
                return;
            }

            // cargar los billetes desde la BD
            billetes.put(10000, rsCajero.getInt("ndiez"));
            billetes.put(20000, rsCajero.getInt("nveinte"));
            billetes.put(50000, rsCajero.getInt("ncincuenta"));
            billetes.put(100000, rsCajero.getInt("ncien"));
            vista.llenarTabla(billetes); // Actualiza la tabla visual

            // validar estado
            int estadoCliente = rsCliente.getInt("estado");
            int estadoCajero = rsCajero.getInt("estado");

            if (estadoCliente == 1 || estadoCajero == 1) {
                JOptionPane.showMessageDialog(vista, "️ Cliente o cajero están ocupados. Intente más tarde.");
                conexion.desconectar(con);
                return;
            }

            // Marcar como "ocupados" temporalmente
            PreparedStatement psUpdateCliente = con.prepareStatement("UPDATE clientes SET estado = 1 WHERE cuenta = ?");
            psUpdateCliente.setString(1, cuenta);
            psUpdateCliente.executeUpdate();

            PreparedStatement psUpdateCajero = con.prepareStatement("UPDATE cajeros SET estado = 1 WHERE id = ?");
            psUpdateCajero.setString(1, numCajero);
            psUpdateCajero.executeUpdate();

            JOptionPane.showMessageDialog(vista, " Cuenta y cajero validados correctamente.");
            cuentaValidada = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al validar: " + e.getMessage());
        } finally {
            conexion.desconectar(con);
        }
    }

    public void agregarNumero(String num) {
        vista.Resultados.setText(vista.Resultados.getText() + num);
    }

    public void corregir() {
        String texto = vista.Resultados.getText();
        if (!texto.isEmpty()) {
            vista.Resultados.setText(texto.substring(0, texto.length() - 1));
        }
    }

    public void cancelarOperacion() {
        vista.Resultados.setText("Operación cancelada.");
        vista.Cuenta.setText("");
        vista.ID_Cajero.setText("");
        cuentaValidada = false;
    }

    public void retirar() {
        if (!cuentaValidada) {
            JOptionPane.showMessageDialog(vista, "Primero valide la cuenta y el cajero.");
            return;
        }

        String texto = vista.Resultados.getText().trim().replaceAll("[^\\d]", "");
        if (texto.isEmpty()) {
            vista.Resultados.setText("Por favor digita un monto.");
            return;
        }

        int monto;
        try {
            monto = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            vista.Resultados.setText("Monto inválido. Solo se permiten números.");
            return;
        }

        if (monto < 10000 || monto > 1000000 || monto % 10000 != 0) {
            vista.Resultados.setText("El monto debe ser entre $10.000 y $1.000.000, y múltiplo de $10.000.");
            return;
        }

        HashMap<Integer, Integer> entrega = generarDistribucion(monto);
        if (entrega == null) {
            vista.Resultados.setText("No hay suficientes billetes para completar el retiro.");
            return;
        }

        // Descontar billetes
        for (int valor : entrega.keySet()) {
            int usados = entrega.get(valor);
            int disponibles = billetes.getOrDefault(valor, 0);
            billetes.put(valor, disponibles - usados);
        }

        //Actualizar la base de datos con los nuevos billetes
        try {
            Cconexion conexion = new Cconexion();
            Connection con = conexion.conectar();

            String sqlUpdateBilletes = "UPDATE cajeros SET ndiez=?, nveinte=?, ncincuenta=?, ncien=? WHERE id=?";
            PreparedStatement psUpdate = con.prepareStatement(sqlUpdateBilletes);
            psUpdate.setInt(1, billetes.get(10000));
            psUpdate.setInt(2, billetes.get(20000));
            psUpdate.setInt(3, billetes.get(50000));
            psUpdate.setInt(4, billetes.get(100000));
            psUpdate.setString(5, vista.ID_Cajero.getText());
            psUpdate.executeUpdate();

            conexion.desconectar(con);
            System.out.println("Billetes del cajero actualizados correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar billetes: " + e.getMessage());
        }

        // Mostrar mensaje de éxito
        StringBuilder mensaje = new StringBuilder("Retiro exitoso de $" + monto + ":\n");
        for (int valor : new int[]{100000, 50000, 20000, 10000}) {
            if (entrega.containsKey(valor)) {
                mensaje.append(entrega.get(valor)).append(" billetes de $").append(valor).append("\n");
            }
        }

        vista.Resultados.setText(mensaje.toString());
        vista.llenarTabla(billetes);
        cuentaValidada = false; // desbloquea tras el retiro
        // Liberar cliente y cajero
        try {
            Cconexion conexion = new Cconexion();
            Connection con = conexion.conectar();

            PreparedStatement psCliente = con.prepareStatement("UPDATE clientes SET estado = 0 WHERE cuenta = ?");
            psCliente.setString(1, vista.Cuenta.getText());
            psCliente.executeUpdate();

            PreparedStatement psCajero = con.prepareStatement("UPDATE cajeros SET estado = 0 WHERE id = ?");
            psCajero.setString(1, vista.ID_Cajero.getText());
            psCajero.executeUpdate();

            conexion.desconectar(con);
        } catch (SQLException e) {
            System.out.println("Error al liberar estado: " + e.getMessage());
        }

    }

    public HashMap<Integer, Integer> generarDistribucion(int monto) {
        HashMap<Integer, Integer> entrega = new HashMap<>();
        int[] denominaciones = {100000, 50000, 20000, 10000};

        for (int valor : denominaciones) {
            int disponibles = billetes.getOrDefault(valor, 0);
            int necesarios = monto / valor;
            int usados = Math.min(necesarios, disponibles);
            if (usados > 0) {
                entrega.put(valor, usados);
                monto -= usados * valor;
            }
        }

        return (monto == 0) ? entrega : null;
    }
}
