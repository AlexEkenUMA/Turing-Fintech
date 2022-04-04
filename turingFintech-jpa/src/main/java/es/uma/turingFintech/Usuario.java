package es.uma.turingFintech;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue
    private String nombre_usuario;
    private String contraseña;
    private boolean administrativo;

    //Constructores


    public Usuario(String nombre_usuario, String contraseña, boolean administrativo) {
        this.nombre_usuario = nombre_usuario;
        this.contraseña = contraseña;
        this.administrativo = administrativo;
    }

    public Usuario(){

    }

    //Getters and Setters


    public String getNombre_usuario() {return nombre_usuario;}

    public void setNombre_usuario(String nombre_usuario) {this.nombre_usuario = nombre_usuario;}

    public String getContraseña() {return contraseña;}

    public void setContraseña(String contraseña) {this.contraseña = contraseña;}

    public boolean isAdministrativo() {return administrativo;}

    public void setAdministrativo(boolean administrativo) {this.administrativo = administrativo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return administrativo == usuario.administrativo && Objects.equals(nombre_usuario, usuario.nombre_usuario) && Objects.equals(contraseña, usuario.contraseña);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre_usuario, contraseña, administrativo);
    }

}
