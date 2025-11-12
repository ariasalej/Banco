/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cconexion;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alejo
 */
public class CControl_admin {
    // === MÉTODO PARA LISTAR CLIENTES ===

    public void listarClientes(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Ciudad");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Saldo");

        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "SELECT * FROM clientes";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("ciudad"),
                    rs.getString("ncuenta"),
                    rs.getDouble("saldo"),
                    rs.getInt("estado")
                }
                );
            }

            tabla.setModel(modelo);
            conexion.desconectar(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar clientes: " + e.getMessage());
        }
    }

// === MÉTODO PARA AGREGAR CLIENTE ===
    public void agregarCliente(String nombre, String apellido, String telefono, String ciudad, String cuenta, double saldo) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "INSERT INTO clientes (nombre, apellido, telefono, ciudad, ncuenta, saldo) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setString(4, ciudad);
            ps.setString(5, cuenta);
            ps.setDouble(6, saldo);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cliente agregado correctamente.");
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar cliente: " + e.getMessage());
        }
    }

    // === MÉTODO PARA EDITAR CLIENTE ===
    public void editarCliente(int id, String nombre, String apellido, String telefono, String ciudad, String cuenta, double saldo) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "UPDATE clientes SET nombre=?, apellido=?, telefono=?, ciudad=?, ncuenta=?, saldo=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setString(4, ciudad);
            ps.setString(5, cuenta);
            ps.setDouble(6, saldo);
            ps.setInt(7, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar cliente: " + e.getMessage());
        }
    }

    // === MÉTODO PARA ELIMINAR CLIENTE ===
    public void eliminarCliente(int id) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "DELETE FROM clientes WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente: " + e.getMessage());
        }
    }

    // === MÉTODO PARA BUSCAR POR ID ===
    public void buscarClientePorId(JTable tabla, int id) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Ciudad");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Saldo");

        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "SELECT * FROM clientes WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("ciudad"),
                    rs.getString("ncuenta"),
                    rs.getDouble("saldo")
                });
            }

            tabla.setModel(modelo);
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + e.getMessage());
        }
    }

    // === MÉTODO PARA BUSCAR POR NOMBRE ===
    public void buscarClientePorNombre(JTable tabla, String nombre) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Ciudad");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Saldo");

        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "SELECT * FROM clientes WHERE nombre LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("ciudad"),
                    rs.getString("ncuenta"),
                    rs.getDouble("saldo")
                });
            }

            tabla.setModel(modelo);
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + e.getMessage());
        }
    }

    // === MÉTODOS DE CONSIGNAR Y RETIRAR ===
    public void consignar(int id, double monto) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "UPDATE clientes SET saldo = saldo + ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, monto);
            ps.setInt(2, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Consignación realizada correctamente.");
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consignar: " + e.getMessage());
        }
    }

    public void retirar(int id, double monto) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "UPDATE clientes SET saldo = saldo - ? WHERE id=? AND saldo >= ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, monto);
            ps.setInt(2, id);
            ps.setDouble(3, monto);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Retiro realizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente o cliente no encontrado.");
            }
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al retirar: " + e.getMessage());
        }
    }

    // ============================================================
    // ============ MÉTODOS PARA ADMINISTRAR CAJEROS ==============
    // ============================================================
    public void listarCajeros(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("$10.000");
        modelo.addColumn("$20.000");
        modelo.addColumn("$50.000");
        modelo.addColumn("$100.000");
        modelo.addColumn("Estado");

        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "SELECT * FROM cajeros";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getInt("ndiez"),
                    rs.getInt("nveinte"),
                    rs.getInt("ncincuenta"),
                    rs.getInt("ncien"),
                    rs.getInt("estado")
                });
            }

            tabla.setModel(modelo);
            conexion.desconectar(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar cajeros: " + e.getMessage());
        }
    }

    public void agregarCajero(int ndiez, int veinte, int cincuenta, int cien) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            // Estado fijo en 0 (disponible)
            String sql = "INSERT INTO cajeros (ndiez, nveinte, ncincuenta, ncien, estado) VALUES (?, ?, ?, ?, 0)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ndiez);
            ps.setInt(2, veinte);
            ps.setInt(3, cincuenta);
            ps.setInt(4, cien);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cajero agregado correctamente.");
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar cajero: " + e.getMessage());
        }
    }

    public void editarCajero(int id, int ndiez, int veinte, int cincuenta, int cien) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "UPDATE cajeros SET ndiez=?, nveinte=?, ncincuenta=?, ncien=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ndiez);
            ps.setInt(2, veinte);
            ps.setInt(3, cincuenta);
            ps.setInt(4, cien);
            ps.setInt(5, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cajero actualizado correctamente.");
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar cajero: " + e.getMessage());
        }
    }

    public void eliminarCajero(int id) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "DELETE FROM cajeros WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cajero eliminado correctamente.");
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cajero: " + e.getMessage());
        }
    }

    public void agregarBilletes(int id, int diez, int veinte, int cincuenta, int cien) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = """
                         UPDATE cajeros 
                         SET ndiez = ndiez + ?, 
                             nveinte = nveinte + ?, 
                             ncincuenta = ncincuenta + ?, 
                             ncien = ncien + ? 
                         WHERE id=?""";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, diez);
            ps.setInt(2, veinte);
            ps.setInt(3, cincuenta);
            ps.setInt(4, cien);
            ps.setInt(5, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Billetes agregados correctamente.");
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar billetes: " + e.getMessage());
        }
    }

    public void quitarBilletes(int id, int diez, int veinte, int cincuenta, int cien) {
        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = """
                         UPDATE cajeros 
                         SET ndiez = GREATEST(ndiez - ?, 0), 
                             nveinte = GREATEST(nveinte - ?, 0), 
                             ncincuenta = GREATEST(ncincuenta - ?, 0), 
                             ncien = GREATEST(ncien - ?, 0) 
                         WHERE id=?""";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, diez);
            ps.setInt(2, veinte);
            ps.setInt(3, cincuenta);
            ps.setInt(4, cien);
            ps.setInt(5, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Billetes retirados correctamente.");
            conexion.desconectar(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al quitar billetes: " + e.getMessage());
        }
    }
    // === BUSCAR CAJERO POR ID ===

    public void buscarCajero(JTable tabla, int id) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("$10.000");
        modelo.addColumn("$20.000");
        modelo.addColumn("$50.000");
        modelo.addColumn("$100.000");
        modelo.addColumn("Estado");

        Cconexion conexion = new Cconexion();
        Connection con = conexion.conectar();

        try {
            String sql = "SELECT * FROM cajeros WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            boolean encontrado = false;

            while (rs.next()) {
                encontrado = true;
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getInt("ndiez"),
                    rs.getInt("nveinte"),
                    rs.getInt("ncincuenta"),
                    rs.getInt("ncien"),
                    rs.getInt("estado")
                });
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "No se encontró ningún cajero con ese ID.");
            }

            tabla.setModel(modelo);
            conexion.desconectar(con);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cajero: " + e.getMessage());
        }
    }
}
