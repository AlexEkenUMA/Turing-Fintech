package es.uma.turingFintech;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class CuentasReferencia extends Cuenta implements Serializable {
    private String Nombre_Banco;
    private String Sucursal;
    private String Pais;
    private Double Saldo;
    private Date Fecha_Apertura;
    private Boolean Estado;

    //Constructores


    public CuentasReferencia(String IBAN, String SWIFT, String nombre_Banco,
                             String sucursal, String pais, Double saldo, Date fecha_Apertura, Boolean estado) {
        super(IBAN, SWIFT);
        Nombre_Banco = nombre_Banco;
        Sucursal = sucursal;
        Pais = pais;
        Saldo = saldo;
        Fecha_Apertura = fecha_Apertura;
        Estado = estado;
    }

    public CuentasReferencia(String nombre_Banco, String sucursal, String pais, Double saldo, Date fecha_Apertura, Boolean estado) {
        Nombre_Banco = nombre_Banco;
        Sucursal = sucursal;
        Pais = pais;
        Saldo = saldo;
        Fecha_Apertura = fecha_Apertura;
        Estado = estado;
    }

    public CuentasReferencia(){

    }

    //Getters and Setters


    public String getNombre_Banco() {
        return Nombre_Banco;
    }

    public void setNombre_Banco(String nombre_Banco) {
        Nombre_Banco = nombre_Banco;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }

    public Date getFecha_Apertura() {
        return Fecha_Apertura;
    }

    public void setFecha_Apertura(Date fecha_Apertura) {
        Fecha_Apertura = fecha_Apertura;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CuentasReferencia that = (CuentasReferencia) o;
        return Objects.equals(Nombre_Banco, that.Nombre_Banco) && Objects.equals(Sucursal, that.Sucursal) && Objects.equals(Pais, that.Pais) && Objects.equals(Saldo, that.Saldo) && Objects.equals(Fecha_Apertura, that.Fecha_Apertura) && Objects.equals(Estado, that.Estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Nombre_Banco, Sucursal, Pais, Saldo, Fecha_Apertura, Estado);
    }
}
