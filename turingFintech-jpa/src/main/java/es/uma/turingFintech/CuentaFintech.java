package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class CuentaFintech extends Cuenta implements Serializable {

    @Temporal(TemporalType.DATE)
    @Column (nullable = false)
    private Date fecha_Apertura;
    @Temporal(TemporalType.DATE)
    private Date fecha_cierre;
    @Column (nullable = false)
    private boolean estado;
    @Column (nullable = false)
    private String tipo;

    //relación uno a muchos cliente-cuenta
    @ManyToOne
    private Cliente cliente;

    //Constructores


    public CuentaFintech(String IBAN, String SWIFT, Date fecha_Apertura, boolean estado, String tipo) {
        super(IBAN, SWIFT);
        this.fecha_Apertura = fecha_Apertura;
        this.estado = estado;
        this.tipo = tipo;
    }

    public CuentaFintech(){

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha_cierre() {return fecha_cierre;}

    public void setFecha_cierre(Date fecha_cierre) {this.fecha_cierre = fecha_cierre;
    }
    //toString


    @Override
    public String toString() {
        return "CuentaFintech{" +
                "fecha_Apertura=" + fecha_Apertura +
                ", fecha_cierre=" + fecha_cierre +
                ", estado=" + estado +
                ", tipo='" + tipo + '\'' +
                "} " + super.toString();
    }
}
