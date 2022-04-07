package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Divisa implements Serializable {
    @Column(nullable = false)
    @Id private String abreviatura;
    @Column(nullable = false)
    private String nombre;
    private char simbolo;
    @Column(nullable = false)
    private double cambio_Euro;

    //Relacion con Transaccion

    //@OneToMany
    //private List<Transaccion> emisor;
    //@OneToMany
    //private List<Transaccion> receptor;

    //Eliminado por unidireccionalidad, descomentar para hacerlo bidirecional


    //relacion uno-muchos divisa-cuentaReferencia

    //@OneToMany
    //private List<CuentaReferencia> cuentasReferencia;

    //Eliminado por unidireccionalidad, descomentar para hacerlo bidirecional

    //Constructores


    public Divisa(String abreviatura, String nombre, char simbolo, double cambio_Euro) {
        this.abreviatura = abreviatura;
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.cambio_Euro = cambio_Euro;
    }

    public Divisa(){

    }

    //Getters and Setters


    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    public double getCambio_Euro() {
        return cambio_Euro;
    }

    public void setCambio_Euro(double cambio_Euro) {
        this.cambio_Euro = cambio_Euro;
    }



    //Equals, hashCode, toString


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
<<<<<<< HEAD
        Divisa divisas = (Divisa) o;
        return Objects.equals(abreviatura, divisas.abreviatura);
=======
        Divisa divisa = (Divisa) o;
        return abreviatura.equals(divisa.abreviatura);
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    }

    @Override
    public int hashCode() {
        return Objects.hash(abreviatura);
<<<<<<< HEAD
=======
    }

    @Override
    public String toString() {
        return "Divisa{" +
                "abreviatura='" + abreviatura + '\'' +
                ", nombre='" + nombre + '\'' +
                ", simbolo=" + simbolo +
                ", cambio_Euro=" + cambio_Euro +
                '}';
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    }
}
