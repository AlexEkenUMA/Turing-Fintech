package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class CuentasFintech extends Cuenta implements Serializable {

    @Temporal(TemporalType.DATE)
    @Column (nullable = false)
    private Date Fecha_Apertura;
    @Column (nullable = false)
    private boolean Estado;
    @Column (nullable = false)
    private String Tipo;
    private Double Saldo;

    //Constructores


    public CuentasFintech(String IBAN, String SWIFT, Date fecha_Apertura, boolean estado, String tipo, Double saldo) {
        super(IBAN, SWIFT);
        Fecha_Apertura = fecha_Apertura;
        Estado = estado;
        Tipo = tipo;
        Saldo = saldo;
    }

    public CuentasFintech(Date fecha_Apertura, boolean estado, String tipo, Double saldo) {
        Fecha_Apertura = fecha_Apertura;
        Estado = estado;
        Tipo = tipo;
        Saldo = saldo;
    }

    public CuentasFintech(){

    }

    //Getters and Setters


    public Date getFecha_Apertura() {
        return Fecha_Apertura;
    }

    public void setFecha_Apertura(Date fecha_Apertura) {
        Fecha_Apertura = fecha_Apertura;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CuentasFintech that = (CuentasFintech) o;
        return Estado == that.Estado && Objects.equals(Fecha_Apertura, that.Fecha_Apertura) && Objects.equals(Tipo, that.Tipo) && Objects.equals(Saldo, that.Saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Fecha_Apertura, Estado, Tipo, Saldo);
    }
}
