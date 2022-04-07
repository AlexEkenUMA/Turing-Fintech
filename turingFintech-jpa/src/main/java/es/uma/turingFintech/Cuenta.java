package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cuenta implements Serializable {
    @Id
    private String IBAN;
    private String SWIFT;
    //SubEntidades de Finctech (Ebury) y de Referencia


    //Relacion con Transacción
    @OneToMany
    private List<Transaccion> origen;
    @OneToMany
    private List<Transaccion> destino;

    //Constructores

    public Cuenta(String IBAN, String SWIFT) {
        this.IBAN = IBAN;
        this.SWIFT = SWIFT;
    }

    public Cuenta(){

    }

    //Getters and Setters


    public String getIBAN() {return IBAN;}

    public void setIBAN(String IBAN) {this.IBAN = IBAN;}

    public String getSWIFT() {return SWIFT;}

    public void setSWIFT(String SWIFT) {this.SWIFT = SWIFT;}

<<<<<<< HEAD
=======
    public List<Transaccion> getOrigen() {
        return origen;
    }

    public void setOrigen(List<Transaccion> origen) {
        this.origen = origen;
    }

    public List<Transaccion> getDestino() {
        return destino;
    }

    public void setDestino(List<Transaccion> destino) {
        this.destino = destino;
    }

    //Equals, hashCode, toString


>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return IBAN.equals(cuenta.IBAN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN);
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "IBAN='" + IBAN + '\'' +
                ", SWIFT='" + SWIFT + '\'' +
<<<<<<< HEAD
                ", origen=" + origen +
                ", destino=" + destino +
=======
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
                '}';
    }

}
