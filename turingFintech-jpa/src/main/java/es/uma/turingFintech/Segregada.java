package es.uma.turingFintech;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Segregada extends CuentaFintech implements Serializable {

    private double comision;

    //Relaciones

    //Relacion 1:1 con Cuenta Referencia
    @OneToOne
    private CuentaReferencia cr;

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

<<<<<<< HEAD
=======
    public CuentaReferencia getCr() {
        return cr;
    }

    public void setCr(CuentaReferencia cr) {
        this.cr = cr;
    }
    //toString


>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    @Override
    public String toString() {
        return "Segregada{" +
                "comision=" + comision +
<<<<<<< HEAD
                ", cr=" + cr +
                "} " + super.toString();
=======
                '}';
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    }

}
