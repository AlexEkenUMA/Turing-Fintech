package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class CuentaReferencia extends Cuenta implements Serializable {
    @Column(nullable = false)
    private String nombre_Banco;
    private String sucursal;
    private String pais;
    @Column(nullable = false)
    private Double saldo;
    @Temporal(TemporalType.DATE)
    private Date fecha_Apertura;
    private Boolean estado;
    //Relaciones

    //relacion 1:1 con Segregada
    @OneToOne
    private Segregada segregada;

    //relacion uno-muchos cuentareferencia-divisa
    @ManyToOne
    private Divisa divisa;

    //relacion uno-muchos pooledaccount-depositadaen
    @OneToMany (mappedBy = "cuentaReferencia")
    private List<DepositadaEn> listaDepositos;

    //Constructores


    public CuentaReferencia(String IBAN, String SWIFT, String nombre_Banco, String sucursal, String pais, Double saldo, Date fecha_Apertura, Boolean estado) {
        super(IBAN, SWIFT);
        this.nombre_Banco = nombre_Banco;
        this.sucursal = sucursal;
        this.pais = pais;
        this.saldo = saldo;
        this.fecha_Apertura = fecha_Apertura;
        this.estado = estado;
    }

    public CuentaReferencia(String nombre_Banco, String sucursal, String pais, Double saldo, Date fecha_Apertura, Boolean estado) {
        this.nombre_Banco = nombre_Banco;
        this.sucursal = sucursal;
        this.pais = pais;
        this.saldo = saldo;
        this.fecha_Apertura = fecha_Apertura;
        this.estado = estado;
    }

    public CuentaReferencia() {

    }

    //Getters and Setters


    public String getNombre_Banco() {
        return nombre_Banco;
    }

    public void setNombre_Banco(String nombre_Banco) {
        this.nombre_Banco = nombre_Banco;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Date getFecha_Apertura() {
        return fecha_Apertura;
    }

    public void setFecha_Apertura(Date fecha_Apertura) {
        this.fecha_Apertura = fecha_Apertura;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CuentaReferencia that = (CuentaReferencia) o;
        return Objects.equals(nombre_Banco, that.nombre_Banco) && Objects.equals(sucursal, that.sucursal) && Objects.equals(pais, that.pais) && Objects.equals(saldo, that.saldo) && Objects.equals(fecha_Apertura, that.fecha_Apertura) && Objects.equals(estado, that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nombre_Banco, sucursal, pais, saldo, fecha_Apertura, estado);
    }
}
