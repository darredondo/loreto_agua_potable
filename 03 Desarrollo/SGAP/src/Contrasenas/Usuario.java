/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contrasenas;

/**
 *
 * @author LUPITA
 */
public class Usuario {
    String nombre;
    String contrasena_nueva;
    String contrasena_anterior;

    @Override
    public String toString() {
        return "Cuenta{" + "nombre=" + nombre + ", contrasena_nueva=" + contrasena_nueva + ", contrasena_anterior=" + contrasena_anterior + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena_nueva() {
        return contrasena_nueva;
    }

    public void setContrasena_nueva(String contrasena_nueva) {
        this.contrasena_nueva = contrasena_nueva;
    }

    public String getContrasena_anterior() {
        return contrasena_anterior;
    }

    public void setContrasena_anterior(String contrasena_anterior) {
        this.contrasena_anterior = contrasena_anterior;
    }
    
    
}
