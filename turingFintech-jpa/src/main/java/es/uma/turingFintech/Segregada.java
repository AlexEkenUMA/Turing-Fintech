package es.uma.turingFintech;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Segregada extends CuentasFintech implements Serializable {

    private double comision;

    public Segregada(String IBAN, String SWIFT, Date fecha_Apertura, boolean estado, String tipo, Double saldo, double comision) {
        super(IBAN, SWIFT, fecha_Apertura, estado, tipo, saldo);
        this.comision = comision;
    }

    public Segregada(Date fecha_Apertura, boolean estado, String tipo, Double saldo, double comision) {
        super(fecha_Apertura, estado, tipo, saldo);
        this.comision = comision;
    }

    public Segregada(double comision) {
        this.comision = comision;
    }

    public Segregada(){

    }

    //Getters and Setters


    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Segregada segregada = (Segregada) o;
        return Double.compare(segregada.comision, comision) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), comision);
    }
}
