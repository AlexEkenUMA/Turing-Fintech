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


<<<<<<< HEAD
    public PersonaJuridica(Long id, String identificacion, String tipo_Cliente, String estado, Date fecha_Alta, Date fecha_Baja,
                           String direccion, String ciudad, Integer codigo_Postal, String pais, String razon_Social) {
=======
    public PersonaJuridica(Long id, Long identificacion, String tipo_Cliente, String estado, Date fecha_Alta, Date fecha_Baja,
                           String direccion, String ciudad, Integer codigo_Postal, String pais,
                           String razon_Social, List<Autorizado> autorizados) {
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
        super(id, identificacion, tipo_Cliente, estado, fecha_Alta, fecha_Baja, direccion, ciudad, codigo_Postal, pais);
        Razon_Social = razon_Social;
        this.autorizados = autorizados;
    }

    public PersonaJuridica(String razon_Social, List<Autorizado> autorizados) {
        Razon_Social = razon_Social;
        this.autorizados = autorizados;
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

<<<<<<< HEAD
=======
    public List<Autorizado> getAutorizados() {
        return autorizados;
    }

    public void setAutorizados(List<Autorizado> autorizados) {
        this.autorizados = autorizados;
    }
    //toString


>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    @Override
    public String toString() {
        return "PersonaJuridica{" +
                "Razon_Social='" + Razon_Social + '\'' +
<<<<<<< HEAD
                "} " + super.toString();
=======
                '}';
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    }

}
