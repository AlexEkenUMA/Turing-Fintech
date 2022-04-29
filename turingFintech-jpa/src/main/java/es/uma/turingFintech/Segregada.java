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
    @OneToOne(mappedBy = "segregada")
    private CuentaReferencia cr;

    public Segregada(String IBAN, String SWIFT, Date fecha_Apertura, String estado, String tipo, double comision) {
        super(IBAN, SWIFT, fecha_Apertura, estado, tipo);
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

    public CuentaReferencia getCr() {
        return cr;
    }

    public void setCr(CuentaReferencia cr) {
        this.cr = cr;
    }


    //toString


    @Override
    public String toString() {
        return "Segregada{" +
                "comision=" + comision +
                "} " + super.toString();
    }

}
