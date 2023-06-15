/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prin;

import config.BDconexion;
import java.sql.ResultSet;

/**
 *
 * @author usuario
 */
public class ControlTarifa {
    /**
     * *
     * Recibe un departamento nuevo y lo almacena en la base de datos.
     *
     * @param nuevo
     */
    public void agregarTarifa(Tarifa nuevo) {
        //Generamos el SQL para insertar un nuevo departamento
        String SQL = "insert into tarifa values(null, '?1','?2')";
        //SQL = SQL.replace("?1", String.valueOf(nuevo.getIdTarifa()));
        SQL = SQL.replace("?1", nuevo.getDescripcion());
        SQL = SQL.replace("?2", nuevo.getCosto_mensual());

        try {
            BDconexion conexion = new BDconexion();
            conexion.conectar();
            conexion.ejecutarActualizacion(SQL);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * *
     * Consulta los departamentos pororden de nombre
     *
     * @return Lista de departamentos
     */
    public ResultSet obtenerTarifa() {
        String SQL = "SELECT * FROM Tarifa order by nombre";

        try {
            BDconexion conexion = new BDconexion();
            conexion.conectar();
            ResultSet resultado = conexion.ejecutarConsulta(SQL);
            return resultado;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * *
     * Consulta los departamentos que coinciden con el filtro de texto
     *
     * @param text - Filtro de nombre de departamento
     * @return Lista de departamentos
     */
    ResultSet obtenerDepartamentos(String text) {
        String SQL = "SELECT * FROM departamento where nombre LIKE '%?1%' order by nombre";
        SQL = SQL.replace("?1", text);
        try {
            BDconexion conexion = new BDconexion();
            conexion.conectar();
            ResultSet resultado = conexion.ejecutarConsulta(SQL);
            return resultado;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
}
