
package Contrasenas;

import Commons.BDconexion;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ControlContrasenna {
    
    public boolean consultarContrasenna(String contrasena) throws SQLException{
        BDconexion conexion=new BDconexion();
        conexion.conectar();
        
        String SQL="select * from usuario where contrasena='?1'";
        SQL=SQL.replace("?1",contrasena);
        ResultSet resultado=conexion.ejecutarConsulta(SQL);
        if(resultado.next())
            return true;
        else
            return false;
        
    }
    
    public void guardarContrasenna(String contrasena_anterior, String contrasena_nueva){
        String SQL = "UPDATE `agua_potable_loreto`.`usuario` SET `contrasena` = 'lupis1234' \n" +
"WHERE (`contrasena` = 'lupis1234');)";
        SQL = SQL.replace("?1", contrasena_anterior);
        SQL = SQL.replace("?2", contrasena_nueva);

        try {
            BDconexion conexion = new BDconexion();
            conexion.conectar();
            conexion.ejecutarActualizacion(SQL);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
     
        
    }
    
}
