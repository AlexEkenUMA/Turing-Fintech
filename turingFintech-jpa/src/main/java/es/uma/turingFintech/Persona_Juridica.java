package es.uma.turingFintech;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Persona_Juridica implements Serializable {
    @Id private Cliente cliente;
    @Column(nullable = false)
    private String Razon_Social;

    //Constructores


    public Persona_Juridica(Cliente cliente, String razon_Social) {
        this.cliente = cliente;
        Razon_Social = razon_Social;
    }

    public Persona_Juridica(){

    }

    //Getters and Setters


    public Cliente getCliente() {return cliente;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public String getRazon_Social() {return Razon_Social;}

    public void setRazon_Social(String razon_Social) {Razon_Social = razon_Social;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona_Juridica that = (Persona_Juridica) o;
        return Objects.equals(cliente, that.cliente) && Objects.equals(Razon_Social, that.Razon_Social);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, Razon_Social);
    }
}
