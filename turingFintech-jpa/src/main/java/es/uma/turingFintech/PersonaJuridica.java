package es.uma.turingFintech;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class PersonaJuridica extends Cliente implements Serializable {

    @Column(nullable = false)
    private String Razon_Social;

    //Constructores


    public PersonaJuridica(Long id, String identificacion, String tipo_Cliente, String estado, Date fecha_Alta, Date fecha_Baja,
                           String direccion, String ciudad, Integer codigo_Postal, String pais, String razon_Social) {
        super(id, identificacion, tipo_Cliente, estado, fecha_Alta, fecha_Baja, direccion, ciudad, codigo_Postal, pais);
        Razon_Social = razon_Social;
    }

    public PersonaJuridica(String razon_Social) {
        Razon_Social = razon_Social;
    }

    public PersonaJuridica(){

    }

    //Getters and Setters


    public String getRazon_Social() {
        return Razon_Social;
    }

    public void setRazon_Social(String razon_Social) {
        Razon_Social = razon_Social;
    }

    @Override
    public String toString() {
        return "PersonaJuridica{" +
                "Razon_Social='" + Razon_Social + '\'' +
                "} " + super.toString();
    }
}
