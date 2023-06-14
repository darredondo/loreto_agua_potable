package Commons;
/*
 Esta clase permite interactual  con una base de datos MySQL
 Requiere la libreria: mysql-connector-java-5.1.6-bin.jar
 */

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BDconexion {
    private final static Logger LOGGER = Logger.getLogger("BDconexion");
    private Connection conexion;
    private Statement comando;

    /**
     * Conecta con un Host de MySQL usando la configuración de ConfigBD.java
     *
     * @return Verdadero - si la conexion es correcta Falso si no se pudo
     * conectar con el servidor
     */
    @SuppressWarnings("empty-statement")
    public boolean conectar() {
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            this.conexion = DriverManager.getConnection("jdbc:mysql://" + ConfigBD.Servidor + "/" + ConfigBD.BaseDatos,
                    ConfigBD.Usuario, ConfigBD.Contrasenna);
            this.comando = conexion.createStatement();
            return true;
        } catch (Exception error) {
            LOGGER.log(Level.INFO,"Error " + error.toString());
            return false;
        }
    }

    /**
     * Este metodo permite ejecutar altas, bajas y cambios sobre los datos y
     * estructuras
     *
     * @param SQL - Es una instrucción SQL DML como INSERT, UPDATE, DELETE o
     * CALL también soporta instrucciones DDL como CREATE, ALTER y DROP
     * @return 0 o más si la instrucción SQL se ejecuto correctamente, -1 en
     * caso de error
     */
    public int ejecutarActualizacion(String SQL) throws SQLException {
        try {
            int FilasAfectadas;
            FilasAfectadas = this.comando.executeUpdate(SQL);
            LOGGER.log(Level.INFO,SQL + " Ejecutada");
            return FilasAfectadas;
        } catch (Exception error) {
            LOGGER.log(Level.INFO,"Error " + error.toString());
            return -1;
        }
    }

    /**
     * Este metodo permite ejecutar altas, bajas y cambios sobre los datos y
     * estructuras
     *
     * @param SQL -Es una instrucción SQL de tipo SELECT o SHOW
     * @return Conjunto de datos del resultado, NULO en caso de error
     */
    public ResultSet ejecutarConsulta(String SQL) {
        try {
            ResultSet resultado = this.comando.executeQuery(SQL);
            LOGGER.log(Level.INFO,SQL + " Ejecutada");
            return resultado;
        } catch (Exception error) {
            LOGGER.log(Level.INFO,"Error " + error.toString());
            return null;
        }
    }

}
