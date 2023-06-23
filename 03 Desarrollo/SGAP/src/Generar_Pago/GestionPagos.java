package Generar_Pago;

import Commons.BDconexion;
import java.sql.ResultSet;


public class GestionPagos {
    public void agregarPago(pago nuevo) {
        //Generamos el SQL para insertar un nuevo departamento
        String SQL = "insert into pago values('?1',now(),'0','?4','?5')";
        SQL = SQL.replace("?1", nuevo.getIdpago());
        //SQL = SQL.replace("?2", nuevo.getFecha_pago());
        //SQL = SQL.replace("?3", nuevo.getMonto());
        SQL = SQL.replace("?4", nuevo.getUsuario_idusuario());
        SQL = SQL.replace("?5", nuevo.getToma_agua_idtoma_agua());

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
    public ResultSet obtenerPago() {
        String SQL = "SELECT * FROM toma_agua";

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

