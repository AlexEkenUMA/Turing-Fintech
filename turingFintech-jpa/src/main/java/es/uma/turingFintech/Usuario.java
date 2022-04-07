package es.uma.turingFintech;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Usuario implements Serializable {

    @Id
    @Column(nullable = false)
    private String nombre_usuario;
    @Column(nullable = false)
    private String contraseña;
    private boolean administrativo;

    //Relaciones 1:1 Persona Autorizada y Cliente

    @OneToOne
    private Autorizado autorizado;

    @OneToOne
    private Cliente cliente;



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

    public Autorizado getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Autorizado autorizado) {
        this.autorizado = autorizado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return nombre_usuario.equals(usuario.nombre_usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre_usuario);
    }
}
