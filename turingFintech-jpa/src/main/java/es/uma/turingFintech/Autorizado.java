package es.uma.turingFintech;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Autorizado implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
<<<<<<< HEAD
    @Column (unique = true, nullable = false)
    private String identificacion;
=======
    @Column(unique = true)
    private Long identificador;
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column(nullable = false)
    private String direccion;
    @Temporal(TemporalType.DATE)
    private Date fecha_Nacimiento;
    private String estado;
    @Temporal(TemporalType.DATE)
    private Date fecha_Inicio;      // (Como autorizado)
    @Temporal(TemporalType.DATE)
    private Date fecha_Fin;         // (Como autorizado)


    //Relacion muchos-muchos con PersonaJuridica
    @ManyToMany
    private List<PersonaJuridica> empresas;

    //Relacion 1:1 usuario
    @OneToOne
    private Usuario usuario;


    //Constructores


<<<<<<< HEAD
    public Autorizado(Long id, String identificacion, String nombre, String apellidos, String direccion, Date fecha_Nacimiento, String estado, Date fecha_Inicio, Date fecha_Fin) {
        this.id = id;
        this.identificacion = identificacion;
=======
    public Autorizado(Long id, Long identificador, String nombre, String apellidos, String direccion, Date fecha_Nacimiento, String estado, Date fecha_Inicio, Date fecha_Fin) {
        this.id = id;
        this.identificador = identificador;
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.fecha_Nacimiento = fecha_Nacimiento;
        this.estado = estado;
        this.fecha_Inicio = fecha_Inicio;
        this.fecha_Fin = fecha_Fin;
    }

    public Autorizado() {

    }

    //Getters and Setters


<<<<<<< HEAD
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
=======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Long identificador) {
        this.identificador = identificador;
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_Nacimiento() {
        return fecha_Nacimiento;
    }

    public void setFecha_Nacimiento(Date fecha_Nacimiento) {
        this.fecha_Nacimiento = fecha_Nacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_Inicio() {
        return fecha_Inicio;
    }

    public void setFecha_Inicio(Date fecha_Inicio) {
        this.fecha_Inicio = fecha_Inicio;
    }

    public Date getFecha_Fin() {
        return fecha_Fin;
    }

    public void setFecha_Fin(Date fecha_Fin) {
        this.fecha_Fin = fecha_Fin;
    }

<<<<<<< HEAD
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

=======
    public List<PersonaJuridica> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<PersonaJuridica> empresas) {
        this.empresas = empresas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    //Equals, Hash and ToString


>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autorizado that = (Autorizado) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
<<<<<<< HEAD
    }

    @Override
    public String toString() {
        return "Autorizado{" +
                "id=" + id +
                ", identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha_Nacimiento=" + fecha_Nacimiento +
                ", estado='" + estado + '\'' +
                ", fecha_Inicio=" + fecha_Inicio +
                ", fecha_Fin=" + fecha_Fin +
                '}';
=======
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    }

    @Override
    public String toString() {
        return "Autorizado{" +
                "id=" + id +
                ", identificador=" + identificador +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha_Nacimiento=" + fecha_Nacimiento +
                ", estado='" + estado + '\'' +
                ", fecha_Inicio=" + fecha_Inicio +
                ", fecha_Fin=" + fecha_Fin +
                '}';
    }

}
