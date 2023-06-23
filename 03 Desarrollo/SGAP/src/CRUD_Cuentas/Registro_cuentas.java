/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUD_Cuentas;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Usuario
 */
public class Registro_cuentas {

    int codigo;
    String usuario;
    String nombre;
    String contrasena;
    String email;
    String tipo;
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void InsertarRegistro(JTextField paramNombre, JTextField paramContrasena, 
            JComboBox paramTipo, JTextField paramEmail, JTextField paramUsuario ){
        setNombre(paramNombre.getText());
        setContrasena(paramContrasena.getText());
        setTipo(paramTipo.getSelectedItem().toString());
        setEmail(paramEmail.getText());
        setUsuario(paramUsuario.getText());
        
        
        Cconexion objetoConexion = new Cconexion();
        
        String consulta ="insert into Usuario (nombre,contrasena,tipo,email,usuario) values (?,?,?,?,?);";
        
        try {
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            
            cs.setString(1, getNombre());
            cs.setString(2, getContrasena());
            cs.setString(3, getTipo());
            cs.setString(4, getEmail());
            cs.setString(5, getUsuario());
            
            cs.execute();
            
            
            JOptionPane.showMessageDialog(null, "Se inserto correctamente el usuario");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se inserto correctamente el usuario, error: "+e.toString());
            
        }
        
    }
    public void MostrarRegistro(JTable paramTablaTotalRegistro){
        Cconexion objetoConexion  = new Cconexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        paramTablaTotalRegistro.setRowSorter(OrdenarTabla);
        
        String sql="";
        
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Contrasena");
        modelo.addColumn("Tipo");
        modelo.addColumn("Email");
        modelo.addColumn("Usuario");
        
        paramTablaTotalRegistro.setModel(modelo);
        
        sql ="Select * from usuario;";
        
        String[] datos = new String[6];
        Statement st;
        
        try{
            st= objetoConexion.establecerConexion().createStatement();
            
             ResultSet rs = st.executeQuery(sql);
             while(rs.next()){
                 //Llenado
                 datos[0]=rs.getString(1);
                 datos[1]=rs.getString(2);
                 datos[2]=rs.getString(3);
                 datos[3]=rs.getString(4);
                 datos[4]=rs.getString(5);
                 datos[5]=rs.getString(6);
                 
                 modelo.addRow(datos);
             }
             
             paramTablaTotalRegistro.setModel(modelo);
             
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,"No se pudo mostrar los registros, error: " + e.toString());
        }
        
    }
    
    public void SeleccionarUsuario(JTable paramTablaRegistro, JTextField paramId, JTextField paramNombre, JTextField paramContraseña, 
            JComboBox paramTipo, JTextField paramEmail, JTextField paramUsuario){
        try {
            int fila = paramTablaRegistro.getSelectedRow();
            
            if (fila >=0) {
                paramId.setText((paramTablaRegistro.getValueAt(fila, 0).toString()));
                paramNombre.setText((paramTablaRegistro.getValueAt(fila, 1).toString()));
                paramContraseña.setText((paramTablaRegistro.getValueAt(fila, 2).toString()));
                paramTipo.setSelectedItem(paramTablaRegistro.getValueAt(fila, 3).toString());
                paramEmail.setText((paramTablaRegistro.getValueAt(fila, 4).toString()));
                paramUsuario.setText((paramTablaRegistro.getValueAt(fila, 5).toString()));
            }
            
            else
            {
                JOptionPane.showMessageDialog(null,"Fila no seleccionada");
            }    
        }catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Error de seleccion, error: "+ e.toString());
        }
    } 
    
    public void ModificarRegistro (JTextField paramCodigo, JTextField paramNombre, JTextField paramContrasena, 
            JComboBox paramTipo, JTextField paramEmail, JTextField paramUsuario){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombre(paramNombre.getText());
        setContrasena(paramContrasena.getText());
        setTipo(paramTipo.getSelectedItem().toString());
        setEmail(paramEmail.getText());
        setUsuario(paramUsuario.getText());
        
        Cconexion objetoConexion = new Cconexion();
        String consulta = "UPDATE Usuario SET usuario.nombre = ?, usuario.contrasena = ?, usuario.tipo = ?, usuario.email = ?, usuario.usuario = ? WHERE usuario.idusuario=?;";
        
        try {
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            
            cs.setString(1, getNombre());
            cs.setString(2, getContrasena());
            cs.setString(3, getTipo());
            cs.setString(4, getEmail());
            cs.setString(5, getUsuario());
            cs.setInt(6, getCodigo());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"Modificacion Exitosa");
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se modifico, error:"+ e.toString());
        }
       }
    public void EliminarRegistro(JTextField paramCodigo){
        
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        
        Cconexion objetoConexion = new Cconexion();
        
        String consulta = "DELETE FROM Usuario WHERE usuario.idusuario=?;";
        
        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, getCodigo());
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"Se ha eliminado correctamente el Usuario");
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,"no se pudo eliminar, error: "+ e.toString());
        }
    }
}