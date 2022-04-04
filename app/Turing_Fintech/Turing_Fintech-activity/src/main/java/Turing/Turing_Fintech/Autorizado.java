package Turing.Turing_Fintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Autorizado implements Serializable {

    @Id private Long Identificacion;
    @Column(nullable = false)
    private String Nombre;
    @Column(nullable = false)
    private String Apellidos;
    @Column(nullable = false)
    private String Direccion;
    @Temporal(TemporalType.DATE)
    private Date Fecha_Nacimiento;
    private String Estado;
    @Temporal(TemporalType.DATE)
    private Date Fecha_Inicio;      // (Como autorizado)
    @Temporal(TemporalType.DATE)
    private Date Fecha_Fin;         // (Como autorizado)


    //Constructores


    public Autorizado(Long identificacion, String nombre, String apellidos, String direccion, Date fecha_Nacimiento, String estado, Date fecha_Inicio, Date fecha_Fin) {
        Identificacion = identificacion;
        Nombre = nombre;
        Apellidos = apellidos;
        Direccion = direccion;
        Fecha_Nacimiento = fecha_Nacimiento;
        Estado = estado;
        Fecha_Inicio = fecha_Inicio;
        Fecha_Fin = fecha_Fin;
    }

    public Autorizado(){

    }

    //Getters and Setters


    public Long getIdentificacion() {return Identificacion;}

    public void setIdentificacion(Long identificacion) {Identificacion = identificacion;}

    public String getNombre() {return Nombre;}

    public void setNombre(String nombre) {Nombre = nombre;}

    public String getApellidos() {return Apellidos;}

    public void setApellidos(String apellidos) {Apellidos = apellidos;}

    public String getDireccion() {return Direccion;}

    public void setDireccion(String direccion) {Direccion = direccion;}

    public Date getFecha_Nacimiento() {return Fecha_Nacimiento;}

    public void setFecha_Nacimiento(Date fecha_Nacimiento) {Fecha_Nacimiento = fecha_Nacimiento;}

    public String getEstado() {return Estado;}

    public void setEstado(String estado) {Estado = estado;}

    public Date getFecha_Inicio() {return Fecha_Inicio;}

    public void setFecha_Inicio(Date fecha_Inicio) {Fecha_Inicio = fecha_Inicio;}

    public Date getFecha_Fin() {return Fecha_Fin;}

    public void setFecha_Fin(Date fecha_Fin) {Fecha_Fin = fecha_Fin;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autorizado that = (Autorizado) o;
        return Objects.equals(Identificacion, that.Identificacion) && Objects.equals(Nombre, that.Nombre) && Objects.equals(Apellidos, that.Apellidos) && Objects.equals(Direccion, that.Direccion) && Objects.equals(Fecha_Nacimiento, that.Fecha_Nacimiento) && Objects.equals(Estado, that.Estado) && Objects.equals(Fecha_Inicio, that.Fecha_Inicio) && Objects.equals(Fecha_Fin, that.Fecha_Fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Identificacion, Nombre, Apellidos, Direccion, Fecha_Nacimiento, Estado, Fecha_Inicio, Fecha_Fin);
    }
}
