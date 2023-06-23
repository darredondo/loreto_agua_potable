/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUD_Cuentas;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Cconexion {
    
    Connection conectar = null;
    
    static public String Servidor="localhost";
    static public String BaseDatos="agua_potable_loreto";
    static public String Usuario="root";
    static public String Contrasena="";
    static public String puerto="3306";
    
    static public String cadena = "jdbc:mysql://"+Servidor+":"+puerto+"/"+BaseDatos;
    
    public Connection establecerConexion(){
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,Usuario,Contrasena);
            JOptionPane.showMessageDialog(null,"La conexion se ha realizado con éxito");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al conectarse a la base de datos error:"+ e.toString());
        }
        return conectar;
    }
    
    
}
