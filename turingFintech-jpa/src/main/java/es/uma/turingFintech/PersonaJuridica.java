package es.uma.turingFintech;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class PersonaJuridica extends Cliente implements Serializable {

    @Column(nullable = false)
    private String Razon_Social;

    //Relacion muchos-muchos con Autorizado
    @ManyToMany(mappedBy = "empresas")
    private List<Autorizado> autorizados;

    //Constructores


    public PersonaJuridica(Long id, Long identificacion, String tipo_Cliente, String estado, Date fecha_Alta, Date fecha_Baja,
                           String direccion, String ciudad, Integer codigo_Postal, String pais,
                           String razon_Social, List<Autorizado> autorizados) {
        super(id, identificacion, tipo_Cliente, estado, fecha_Alta, fecha_Baja, direccion, ciudad, codigo_Postal, pais);
        Razon_Social = razon_Social;
        this.autorizados = autorizados;
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

    public List<Autorizado> getAutorizados() {
        return autorizados;
    }

    public void setAutorizados(List<Autorizado> autorizados) {
        this.autorizados = autorizados;
    }



    //toString


    @Override
    public String toString() {
        return "PersonaJuridica{" +
                "Razon_Social='" + Razon_Social + '\'' +
                "} " + super.toString();
    }
}
