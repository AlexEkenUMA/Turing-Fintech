package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CuentasFintech extends Cuenta implements Serializable {

    @Temporal(TemporalType.DATE)
    @Column (nullable = false)
    private Date fecha_Apertura;
    @Column (nullable = false)
    private boolean estado;
    @Column (nullable = false)
    private String tipo;
    private Double saldo;

    //relación uno a muchos cliente-cuenta
    @ManyToOne
    private Cliente cliente;

    //Constructores


    public CuentasFintech(String IBAN, String SWIFT, Date fecha_Apertura, boolean estado, String tipo, Double saldo) {
        super(IBAN, SWIFT);
        this.fecha_Apertura = fecha_Apertura;
        this.estado = estado;
        this.tipo = tipo;
        this.saldo = saldo;
    }

    public CuentasFintech(Date fecha_Apertura, boolean estado, String tipo, Double saldo) {
        this.fecha_Apertura = fecha_Apertura;
        this.estado = estado;
        this.tipo = tipo;
        this.saldo = saldo;
    }

    public CuentasFintech(){

    }

    //Getters and Setters


    public Date getFecha_Apertura() {
        return fecha_Apertura;
    }

    public void setFecha_Apertura(Date fecha_Apertura) {
        this.fecha_Apertura = fecha_Apertura;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CuentasFintech that = (CuentasFintech) o;
        return estado == that.estado && Objects.equals(fecha_Apertura, that.fecha_Apertura) && Objects.equals(tipo, that.tipo) && Objects.equals(saldo, that.saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fecha_Apertura, estado, tipo, saldo);
    }
}
