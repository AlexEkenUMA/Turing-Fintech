package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Autorizado implements Serializable {

    @Id
    private Long identificacion;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column(nullable = false)
    private String direccion;
    @Temporal(TemporalType.DATE)
    private Date fecha_Nacimiento;
    private String estado;
    @Temporal(TemporalType.DATE)
    private Date fecha_Inicio;      // (Como autorizado)
    @Temporal(TemporalType.DATE)
    private Date fecha_Fin;         // (Como autorizado)


    //Constructores


    public Autorizado(Long identificacion, String nombre, String apellidos, String direccion, Date fecha_Nacimiento, String estado, Date fecha_Inicio, Date fecha_Fin) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.fecha_Nacimiento = fecha_Nacimiento;
        this.estado = estado;
        this.fecha_Inicio = fecha_Inicio;
        this.fecha_Fin = fecha_Fin;
    }

    public Autorizado() {

    }

    //Getters and Setters


    public Long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Long identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_Nacimiento() {
        return fecha_Nacimiento;
    }

    public void setFecha_Nacimiento(Date fecha_Nacimiento) {
        this.fecha_Nacimiento = fecha_Nacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_Inicio() {
        return fecha_Inicio;
    }

    public void setFecha_Inicio(Date fecha_Inicio) {
        this.fecha_Inicio = fecha_Inicio;
    }

    public Date getFecha_Fin() {
        return fecha_Fin;
    }

    public void setFecha_Fin(Date fecha_Fin) {
        this.fecha_Fin = fecha_Fin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autorizado that = (Autorizado) o;
        return Objects.equals(identificacion, that.identificacion) && Objects.equals(nombre, that.nombre) && Objects.equals(apellidos, that.apellidos) && Objects.equals(direccion, that.direccion) && Objects.equals(fecha_Nacimiento, that.fecha_Nacimiento) && Objects.equals(estado, that.estado) && Objects.equals(fecha_Inicio, that.fecha_Inicio) && Objects.equals(fecha_Fin, that.fecha_Fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion, nombre, apellidos, direccion, fecha_Nacimiento, estado, fecha_Inicio, fecha_Fin);
    }
}
