package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Transaccion implements Serializable {

    @Id
    @GeneratedValue
    private Long iD_Unico;
    @Column(nullable = false)
    private Date fecha_Instruccion;
    @Column(nullable = false)
    private Double cantidad;
    private String concepto;
    private String nombre_Emisor;
    private Double comision;
    @Column(nullable = false)
    private String tipo_Transaccion;

    //Relacion con Divisas
    @ManyToOne
    private Divisa receptor;
    @ManyToOne
    private Divisa emisor;

    //Relacion con Cuenta
    @ManyToOne
    private Cuenta origen;
    @ManyToOne
    private Cuenta destino;


    //Constructores


    public Transaccion(Long iD_Unico, Date fecha_Instruccion, Double cantidad, String concepto, String nombre_Emisor, Double comision, String tipo_Transaccion) {
        this.iD_Unico = iD_Unico;
        this.fecha_Instruccion = fecha_Instruccion;
        this.cantidad = cantidad;
        this.concepto = concepto;
        this.nombre_Emisor = nombre_Emisor;
        this.comision = comision;
        this.tipo_Transaccion = tipo_Transaccion;
    }

    public Transaccion(){

    }

    //Getters and Setters


    public Long getiD_Unico() {
        return iD_Unico;
    }

    public void setiD_Unico(Long iD_Unico) {
        this.iD_Unico = iD_Unico;
    }

    public Date getFecha_Instruccion() {
        return fecha_Instruccion;
    }

    public void setFecha_Instruccion(Date fecha_Instruccion) {
        this.fecha_Instruccion = fecha_Instruccion;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNombre_Emisor() {
        return nombre_Emisor;
    }

    public void setNombre_Emisor(String nombre_Emisor) {
        this.nombre_Emisor = nombre_Emisor;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public String getTipo_Transaccion() {
        return tipo_Transaccion;
    }

    public void setTipo_Transaccion(String tipo_Transaccion) {
        this.tipo_Transaccion = tipo_Transaccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return Objects.equals(iD_Unico, that.iD_Unico) && Objects.equals(fecha_Instruccion, that.fecha_Instruccion) && Objects.equals(cantidad, that.cantidad) && Objects.equals(concepto, that.concepto) && Objects.equals(nombre_Emisor, that.nombre_Emisor) && Objects.equals(comision, that.comision) && Objects.equals(tipo_Transaccion, that.tipo_Transaccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iD_Unico, fecha_Instruccion, cantidad, concepto, nombre_Emisor, comision, tipo_Transaccion);
    }
}
