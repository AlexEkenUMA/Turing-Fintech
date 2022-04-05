package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class PersonaFisica extends Cliente implements Serializable {
    @Column(nullable = false)
    private String Nombre;
    @Column(nullable = false)
    private String Apellidos;
    @Temporal(TemporalType.DATE)
    private Date Fecha_Nacimiento;

    //Constructores
    public PersonaFisica(Long id, String tipo_Cliente, String estado, Date fecha_Alta,
                         Date fecha_Baja, String direccion, String ciudad,
                         Integer codigo_Postal, String pais, String nombre, String apellidos, Date fecha_Nacimiento) {
        super(id, tipo_Cliente, estado, fecha_Alta, fecha_Baja, direccion, ciudad, codigo_Postal, pais);
        Nombre = nombre;
        Apellidos = apellidos;
        Fecha_Nacimiento = fecha_Nacimiento;
    }

    public PersonaFisica(String nombre, String apellidos, Date fecha_Nacimiento) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonaFisica that = (PersonaFisica) o;
        return Objects.equals(Nombre, that.Nombre) && Objects.equals(Apellidos, that.Apellidos) && Objects.equals(Fecha_Nacimiento, that.Fecha_Nacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Nombre, Apellidos, Fecha_Nacimiento);
    }
}
