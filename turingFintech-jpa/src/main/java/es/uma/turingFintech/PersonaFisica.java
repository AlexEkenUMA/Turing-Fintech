package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class PersonaFisica extends Cliente implements Serializable {
    //@Column(nullable = false)
    private String Nombre;
   // @Column(nullable = false)
    private String Apellidos;
    @Temporal(TemporalType.DATE)
    private Date Fecha_Nacimiento;

    //Constructores
    public PersonaFisica(Long id, Long identificacion, String tipo_Cliente, String estado, Date fecha_Alta, Date fecha_Baja,
                         String direccion, String ciudad, Integer codigo_Postal, String pais, String nombre,
                         String apellidos, Date fecha_Nacimiento) {
        super(id, identificacion, tipo_Cliente, estado, fecha_Alta, fecha_Baja, direccion, ciudad, codigo_Postal, pais);
        Nombre = nombre;
        Apellidos = apellidos;
        Fecha_Nacimiento = fecha_Nacimiento;
    }

    public PersonaFisica(){
    }

    //Getters and Setters


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public Date getFecha_Nacimiento() {
        return Fecha_Nacimiento;
    }

    public void setFecha_Nacimiento(Date fecha_Nacimiento) {
        Fecha_Nacimiento = fecha_Nacimiento;
    }






    //toString


    @Override
    public String toString() {
        return "PersonaFisica{" +
                "Nombre='" + Nombre + '\'' +
                ", Apellidos='" + Apellidos + '\'' +
                ", Fecha_Nacimiento=" + Fecha_Nacimiento +
                "} " + super.toString();
    }

}
