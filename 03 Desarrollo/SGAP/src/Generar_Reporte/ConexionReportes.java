package Generar_Reporte;
import Commons.BDconexion;
import java.sql.ResultSet;

public class ConexionReportes {
    //Obetern la lista de tomas de agua activas
    public ResultSet obtenerListaTomasActivas() {
        String SQL = "SELECT * FROM toma_agua where activa=1";

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
    
    //Obtener la lista de pagos
    public ResultSet obtenerPagosMensuales() {
        String SQL = "SELECT * FROM pago where monto=0";
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
     //Obtener los pagos de un periodo en especifico
    ResultSet obtenerPagosPeriodoEspesifico(String anyo, String mes) {
        String SQL = "SELECT * FROM agua_potable_loreto.pago where year(fecha_pago)=?1 and month(fecha_pago)=?2";
        SQL=SQL.replace("?1", anyo);
        SQL=SQL.replace("?2", mes);
        
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

    //Obtener una toma espesifica
    ResultSet obtenerTomaEspesifica(String idPropietario) {
        String SQL = "SELECT * FROM pago where toma_agua_idtoma_agua=?1";
        SQL=SQL.replace("?1", idPropietario);
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
