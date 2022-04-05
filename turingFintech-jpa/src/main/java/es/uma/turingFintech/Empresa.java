package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Empresa extends Cliente implements Serializable {

    @Column(nullable = false)
    private String razon_Social;

    //Relacion con Autorizado
    @ManyToMany (mappedBy = "empresas")
    private List<Autorizado> autorizados;



    //Constructores

    public Empresa(Long id, String tipo_Cliente, String estado, Date fecha_Alta, Date fecha_Baja, String direccion, String ciudad, Integer codigo_Postal, String pais, String razon_Social) {
        super(id, tipo_Cliente, estado, fecha_Alta, fecha_Baja, direccion, ciudad, codigo_Postal, pais);
        this.razon_Social = razon_Social;
    }

    public Empresa(String razon_Social) {
        this.razon_Social = razon_Social;
    }

    public Empresa() {
    }

    public String getRazon_Social() {
        return razon_Social;
    }

    public void setRazon_Social(String razon_Social) {
        this.razon_Social = razon_Social;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(razon_Social, empresa.razon_Social);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), razon_Social);
    }
}




