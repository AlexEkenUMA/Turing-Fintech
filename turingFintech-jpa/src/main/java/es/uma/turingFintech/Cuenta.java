package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cuenta implements Serializable {
    @Id
    @GeneratedValue
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(IBAN, cuenta.IBAN) && Objects.equals(SWIFT, cuenta.SWIFT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN, SWIFT);
    }
}
