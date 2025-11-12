/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Banco;

/**
 *
 * @author Alejo
 */
import Vista.Interfaz;

public class Banco {

    public static void main(String[] args) {

        Interfaz in = new Interfaz();
        in.setVisible(true);
        
         // try {

            //este tipo de consulta es para insertar, editar y eliminar
            //query = "INSERT INTO clientes (id, nombre, apellido, telefono, ciudad, ncuenta, saldo, estado) VALUES (NULL, 'Christopher', 'Valencia', '3142265784', 'Manizales', 777, 1000000, 0);";
            //query = "UPDATE clientes SET nombre = 'Chis',apellido = 'Valencia',telefono = '3142265784',ciudad = 'Manizales',ncuenta = 777,saldo = 10000000,estado = 0 WHERE id = 10";// editar
            //query = "DELETE FROM clientes WHERE id= 4";
            //PreparedStatement ps = con.prepareStatement(query);
            //ps.executeUpdate();

            //System.out.println("Consulta correcta");
       // } catch (SQLException ex) {
           // System.out.println("Error en el sql");
       // }
    }
}
