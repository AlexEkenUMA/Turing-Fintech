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
    @OneToMany
    private List<Transaccion> emisor;
    @OneToMany
    private List<Transaccion> receptor;

    //relacion uno-muchos divisa-cuentaReferencia
    @OneToMany
    private List<CuentasReferencia> cuentasReferencia;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divisa divisas = (Divisa) o;
        return simbolo == divisas.simbolo && Double.compare(divisas.cambio_Euro, cambio_Euro) == 0 && Objects.equals(abreviatura, divisas.abreviatura) && Objects.equals(nombre, divisas.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abreviatura, nombre, simbolo, cambio_Euro);
    }
}
