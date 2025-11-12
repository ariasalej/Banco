/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Alejo
 */
public class Cconexion {

    public static Connection conectar() {
        String usuario = "alejandro";
        String contrasena = "#3Programacion3";
        String url = "jdbc:mysql://72.167.84.254/banco";

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("conexion correcta");
        } catch (Exception e) {
            System.out.println("Conexion incorrecta");
        }
        return con;
    }

    public void desconectar(Connection con) {
        try {
            con.close();
            System.out.println("se cerro la conexion");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
