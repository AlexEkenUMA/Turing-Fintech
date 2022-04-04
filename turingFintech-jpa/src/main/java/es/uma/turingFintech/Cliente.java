package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Cliente implements Serializable {
    @Id private Long ID;
    @Column (nullable = false)
    private String Tipo_Cliente; // Habría que hacer dos subentidades --> Persona Física y Jurídica
    @Column (nullable = false)
    private String Estado;
    @Temporal(TemporalType.DATE)
    private Date Fecha_Alta;
    @Temporal(TemporalType.DATE)
    private Date Fecha_Baja;
    @Column (nullable = false)
    private String Direccion;
    @Column (nullable = false)
    private String Ciudad;
    private Integer Codigo_Postal;
    @Column (nullable = false)
    private String Pais;

    //Constructores

    public Cliente(Long ID, String tipo_Cliente, String estado, Date fecha_Alta, Date fecha_Baja, String direccion, String ciudad, Integer codigo_Postal, String pais) {
        this.ID = ID;
        Tipo_Cliente = tipo_Cliente;
        Estado = estado;
        Fecha_Alta = fecha_Alta;
        Fecha_Baja = fecha_Baja;
        Direccion = direccion;
        Ciudad = ciudad;
        Codigo_Postal = codigo_Postal;
        Pais = pais;
    }

    public Cliente(){

    }

    //Getters and Setters
    public Long getID() {return ID;}

    public void setID(Long ID) {this.ID = ID;}

    public String getTipo_Cliente() {return Tipo_Cliente;}

    public void setTipo_Cliente(String tipo_Cliente) {Tipo_Cliente = tipo_Cliente;}

    public String getEstado() {return Estado;}

    public void setEstado(String estado) {Estado = estado;}

    public Date getFecha_Alta() {return Fecha_Alta;}

    public void setFecha_Alta(Date fecha_Alta) {Fecha_Alta = fecha_Alta;}

    public Date getFecha_Baja() {return Fecha_Baja;}

    public void setFecha_Baja(Date fecha_Baja) {Fecha_Baja = fecha_Baja;}

    public String getDireccion() {return Direccion;}

    public void setDireccion(String direccion) {Direccion = direccion;}

    public String getCiudad() {return Ciudad;}

    public void setCiudad(String ciudad) {Ciudad = ciudad;}

    public Integer getCodigo_Postal() {return Codigo_Postal;}

    public void setCodigo_Postal(Integer codigo_Postal) {Codigo_Postal = codigo_Postal;}

    public String getPais() {return Pais;}

    public void setPais(String pais) {Pais = pais;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(ID, cliente.ID) && Objects.equals(Tipo_Cliente, cliente.Tipo_Cliente) && Objects.equals(Estado, cliente.Estado) && Objects.equals(Fecha_Alta, cliente.Fecha_Alta) && Objects.equals(Fecha_Baja, cliente.Fecha_Baja) && Objects.equals(Direccion, cliente.Direccion) && Objects.equals(Ciudad, cliente.Ciudad) && Objects.equals(Codigo_Postal, cliente.Codigo_Postal) && Objects.equals(Pais, cliente.Pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Tipo_Cliente, Estado, Fecha_Alta, Fecha_Baja, Direccion, Ciudad, Codigo_Postal, Pais);
    }
}
