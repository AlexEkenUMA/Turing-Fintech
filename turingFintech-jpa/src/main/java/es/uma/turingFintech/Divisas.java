package es.uma.turingFintech;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Divisas implements Serializable {
    @Id private String Abreviatura;
    private String Nombre;
    private char Simbolo;
    private double Cambio_Euro;
    //Relacion con Transaccion

    //Constructores


    public Divisas(String abreviatura, String nombre, char simbolo, double cambio_Euro) {
        Abreviatura = abreviatura;
        Nombre = nombre;
        Simbolo = simbolo;
        Cambio_Euro = cambio_Euro;
    }

    public Divisas(){

    }

    //Getters and Setters


    public String getAbreviatura() {return Abreviatura;}

    public void setAbreviatura(String abreviatura) {Abreviatura = abreviatura;}

    public String getNombre() {return Nombre;}

    public void setNombre(String nombre) {Nombre = nombre;}

    public char getSimbolo() {return Simbolo;}

    public void setSimbolo(char simbolo) {Simbolo = simbolo;}

    public double getCambio_Euro() {return Cambio_Euro;}

    public void setCambio_Euro(double cambio_Euro) {Cambio_Euro = cambio_Euro;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divisas divisas = (Divisas) o;
        return Simbolo == divisas.Simbolo && Double.compare(divisas.Cambio_Euro, Cambio_Euro) == 0 && Objects.equals(Abreviatura, divisas.Abreviatura) && Objects.equals(Nombre, divisas.Nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Abreviatura, Nombre, Simbolo, Cambio_Euro);
    }
}
