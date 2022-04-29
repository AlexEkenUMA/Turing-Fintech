package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column (unique = true, nullable = false)
    private String identificacion;
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

    //relación uno a muchos cliente-cuenta
    @OneToMany(mappedBy = "cliente")
    private List<CuentaFintech> cuentasFintech;

    //Relacion 1:1 usuario
    @OneToOne(mappedBy = "cliente")
    private Usuario usuario;

    //Constructores


    public Cliente(Long id, String identificacion, String tipo_Cliente, String estado,
                   Date fecha_Alta, Date fecha_Baja, String direccion, String ciudad, Integer codigo_Postal, String pais) {
        this.id = id;
        this.identificacion = identificacion;
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public List<CuentaFintech> getCuentasFintech() {
        return cuentasFintech;
    }

    public void setCuentasFintech(List<CuentaFintech> cuentasFintech) {
        this.cuentasFintech = cuentasFintech;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    //Equals, hashcode, toString


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", identificacion=" + identificacion +
                ", tipo_Cliente='" + tipo_Cliente + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha_Alta=" + fecha_Alta +
                ", fecha_Baja=" + fecha_Baja +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", codigo_Postal=" + codigo_Postal +
                ", pais='" + pais + '\'' +
                '}';
    }

}
