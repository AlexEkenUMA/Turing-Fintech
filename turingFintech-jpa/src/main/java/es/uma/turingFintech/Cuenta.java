package es.uma.turingFintech;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Cuenta implements Serializable {
    @Id
    @GeneratedValue
    private String IBAN;
    private String SWIFT;
    //SubEntidades de Finctech (Ebury) y de Referencia

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
