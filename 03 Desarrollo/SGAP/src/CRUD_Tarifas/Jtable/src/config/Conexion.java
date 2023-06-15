package config;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author usuario
 */
public class Conexion {
    Connection con;
    
    public Conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/agua_potable_loreto", "root", "");
            
        } catch (Exception e) {
            System.err.println("No se pudo establecer la conexion con la base de datos. Error: " + e);
        }
    }
    
    public Connection getConnection(){
        return con;
    } 
}
