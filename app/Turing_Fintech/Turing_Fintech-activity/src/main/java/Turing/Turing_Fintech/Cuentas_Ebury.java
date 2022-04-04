package Turing.Turing_Fintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Cuentas_Ebury implements Serializable {
    @Id private Cuenta cuenta;
    @Temporal(TemporalType.DATE)
    @Column (nullable = false)
    private Date Fecha_Apertura;
    @Column (nullable = false)
    private boolean Estado;
    @Column (nullable = false)
    private String Tipo;
    private Double Saldo;

    //Constructores


    public Cuentas_Ebury(Cuenta cuenta, Date fecha_Apertura, boolean estado, String tipo, Double saldo) {
        this.cuenta = cuenta;
        Fecha_Apertura = fecha_Apertura;
        Estado = estado;
        Tipo = tipo;
        Saldo = saldo;
    }

    public Cuentas_Ebury(){

    }

    //Getters and Setters


    public Cuenta getCuenta() {return cuenta;}

    public void setCuenta(Cuenta cuenta) {this.cuenta = cuenta;}

    public Date getFecha_Apertura() {return Fecha_Apertura;}

    public void setFecha_Apertura(Date fecha_Apertura) {Fecha_Apertura = fecha_Apertura;}

    public boolean isEstado() {return Estado;}

    public void setEstado(boolean estado) {Estado = estado;}

    public String getTipo() {return Tipo;}

    public void setTipo(String tipo) {Tipo = tipo;}

    public Double getSaldo() {return Saldo;}

    public void setSaldo(Double saldo) {Saldo = saldo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuentas_Ebury that = (Cuentas_Ebury) o;
        return Estado == that.Estado && Objects.equals(cuenta, that.cuenta) && Objects.equals(Fecha_Apertura, that.Fecha_Apertura) && Objects.equals(Tipo, that.Tipo) && Objects.equals(Saldo, that.Saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cuenta, Fecha_Apertura, Estado, Tipo, Saldo);
    }
}
