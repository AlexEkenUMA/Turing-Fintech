package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Cliente implements Serializable {
    @Id private Long id;
    @Column (nullable = false)
    private String tipo_Cliente; // Habría que hacer dos subentidades --> Persona Física y Jurídica
    @Column (nullable = false)
    private String estado;
    @Temporal(TemporalType.DATE)
    private Date fecha_Alta;
    @Temporal(TemporalType.DATE)
    private Date fecha_Baja;
    @Column (nullable = false)
    private String direccion;
    @Column (nullable = false)
    private String ciudad;
    private Integer codigo_Postal;
    @Column (nullable = false)
    private String pais;

    //Constructores


    public Cliente(Long id, String tipo_Cliente, String estado, Date fecha_Alta, Date fecha_Baja, String direccion, String ciudad, Integer codigo_Postal, String pais) {
        this.id = id;
        this.tipo_Cliente = tipo_Cliente;
        this.estado = estado;
        this.fecha_Alta = fecha_Alta;
        this.fecha_Baja = fecha_Baja;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigo_Postal = codigo_Postal;
        this.pais = pais;
    }

    public Cliente(){

    }

    //Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo_Cliente() {
        return tipo_Cliente;
    }

    public void setTipo_Cliente(String tipo_Cliente) {
        this.tipo_Cliente = tipo_Cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_Alta() {
        return fecha_Alta;
    }

    public void setFecha_Alta(Date fecha_Alta) {
        this.fecha_Alta = fecha_Alta;
    }

    public Date getFecha_Baja() {
        return fecha_Baja;
    }

    public void setFecha_Baja(Date fecha_Baja) {
        this.fecha_Baja = fecha_Baja;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getCodigo_Postal() {
        return codigo_Postal;
    }

    public void setCodigo_Postal(Integer codigo_Postal) {
        this.codigo_Postal = codigo_Postal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(tipo_Cliente, cliente.tipo_Cliente) && Objects.equals(estado, cliente.estado) && Objects.equals(fecha_Alta, cliente.fecha_Alta) && Objects.equals(fecha_Baja, cliente.fecha_Baja) && Objects.equals(direccion, cliente.direccion) && Objects.equals(ciudad, cliente.ciudad) && Objects.equals(codigo_Postal, cliente.codigo_Postal) && Objects.equals(pais, cliente.pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo_Cliente, estado, fecha_Alta, fecha_Baja, direccion, ciudad, codigo_Postal, pais);
    }

}
