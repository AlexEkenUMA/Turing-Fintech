package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Persona_Fisica implements Serializable {
    @Id private Cliente cliente;
    @Column(nullable = false)
    private String Nombre;
    @Column(nullable = false)
    private String Apellidos;
    @Temporal(TemporalType.DATE)
    private Date Fecha_Nacimiento;

    //Constructores


    public Persona_Fisica(Cliente cliente, String nombre, String apellidos, Date fecha_Nacimiento) {
        this.cliente = cliente;
        Nombre = nombre;
        Apellidos = apellidos;
        Fecha_Nacimiento = fecha_Nacimiento;
    }

    public Persona_Fisica(){
    }

    //Getters and Setters

    public Cliente getCliente() {return cliente;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public String getNombre() {return Nombre;}

    public void setNombre(String nombre) {Nombre = nombre;}

    public String getApellidos() {return Apellidos;}

    public void setApellidos(String apellidos) {Apellidos = apellidos;}

    public Date getFecha_Nacimiento() {return Fecha_Nacimiento;}

    public void setFecha_Nacimiento(Date fecha_Nacimiento) {Fecha_Nacimiento = fecha_Nacimiento;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona_Fisica that = (Persona_Fisica) o;
        return Objects.equals(cliente, that.cliente) && Objects.equals(Nombre, that.Nombre) && Objects.equals(Apellidos, that.Apellidos) && Objects.equals(Fecha_Nacimiento, that.Fecha_Nacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, Nombre, Apellidos, Fecha_Nacimiento);
    }


}
