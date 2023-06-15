
package prin;

/**
 *
 * @author usuario
 */
public class Tarifa {
    int idTarifa;
    String descripcion;
    String costo_mensual;
    
    @Override
    public String toString() {
        return "Tarifa{" + "idTarifa=" + idTarifa+ ", descripcion=" + descripcion + ", costo_mensual=" + costo_mensual + '}';
    }

    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCosto_mensual() {
        return costo_mensual;
    }

    public void setCosto_mensual(String costo_mensual) {
        this.costo_mensual = costo_mensual;
    }
    
    
}
