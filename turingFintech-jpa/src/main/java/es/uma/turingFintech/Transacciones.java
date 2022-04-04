package es.uma.turingFintech;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Transacciones implements Serializable {

    @Id private Long ID_Unico;
    private Date Fecha_Instruccion;
    private Double Cantidad;
    private String Concepto;
    private String Nombre_Emisor;
    private Double Comision;
    private String Tipo_Transaccion;
    //Relacion con Divisas

    //Constructores


    public Transacciones(Long ID_Unico, Date fecha_Instruccion, Double cantidad, String concepto, String nombre_Emisor, Double comision, String tipo_Transaccion) {
        this.ID_Unico = ID_Unico;
        Fecha_Instruccion = fecha_Instruccion;
        Cantidad = cantidad;
        Concepto = concepto;
        Nombre_Emisor = nombre_Emisor;
        Comision = comision;
        Tipo_Transaccion = tipo_Transaccion;
    }

    public Transacciones(){

    }

    //Getters and Setters


    public Long getID_Unico() {return ID_Unico;}

    public void setID_Unico(Long ID_Unico) {this.ID_Unico = ID_Unico;}

    public Date getFecha_Instruccion() {return Fecha_Instruccion;}

    public void setFecha_Instruccion(Date fecha_Instruccion) {Fecha_Instruccion = fecha_Instruccion;}

    public Double getCantidad() {return Cantidad;}

    public void setCantidad(Double cantidad) {Cantidad = cantidad;}

    public String getConcepto() {return Concepto;}

    public void setConcepto(String concepto) {Concepto = concepto;}

    public String getNombre_Emisor() {return Nombre_Emisor;}

    public void setNombre_Emisor(String nombre_Emisor) {Nombre_Emisor = nombre_Emisor;}

    public Double getComision() {return Comision;}

    public void setComision(Double comision) {Comision = comision;}

    public String getTipo_Transaccion() {return Tipo_Transaccion;}

    public void setTipo_Transaccion(String tipo_Transaccion) {Tipo_Transaccion = tipo_Transaccion;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacciones that = (Transacciones) o;
        return Objects.equals(ID_Unico, that.ID_Unico) && Objects.equals(Fecha_Instruccion, that.Fecha_Instruccion) && Objects.equals(Cantidad, that.Cantidad) && Objects.equals(Concepto, that.Concepto) && Objects.equals(Nombre_Emisor, that.Nombre_Emisor) && Objects.equals(Comision, that.Comision) && Objects.equals(Tipo_Transaccion, that.Tipo_Transaccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_Unico, Fecha_Instruccion, Cantidad, Concepto, Nombre_Emisor, Comision, Tipo_Transaccion);
    }
}
