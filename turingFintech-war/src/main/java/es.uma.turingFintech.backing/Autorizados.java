package es.uma.turingFintech.backing;

import clases.ejb.GestionAutorizados;
import clases.ejb.GestionClientes;
import clases.ejb.GestionUsuarios;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.PersonaJuridica;
import es.uma.turingFintech.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named(value = "Autorizados")
@RequestScoped
public class Autorizados {

    @Inject
    private InfoSesion sesion;

    @Inject
    private GestionClientes gestionClientes;

    @Inject
    private GestionAutorizados gestionAutorizados;

    @Inject
    private GestionUsuarios gestionUsuarios;
    String dni= "";

    Long id;

    private PersonaJuridica personaJuridica;

    Autorizado autorizado;

    private Usuario usuario;

    private Usuario nuevo;

    String repass = "";


    public Autorizados(){
        personaJuridica = new PersonaJuridica();
        autorizado = new Autorizado();
        nuevo = new Usuario();
    }

    public Usuario getNuevo() {return nuevo;}

    public void setNuevo(Usuario nuevo) {this.nuevo = nuevo;}

    public PersonaJuridica getPersonaJuridica() {
        return personaJuridica;
    }

    public Autorizado getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Autorizado autorizado) {
        this.autorizado = autorizado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setPersonaJuridica(PersonaJuridica personaJuridica) {
        this.personaJuridica = personaJuridica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getRepass() {return repass;}

    public void setRepass(String repass) {this.repass = repass;}

    public String anadirAuto(PersonaJuridica pj, Usuario u){
        personaJuridica = pj;
        usuario = u;
        dni = pj.getIdentificacion();
        id = pj.getId();
        return "listaAutorizados.xhtml";
    }

    public String seleccion (Autorizado au, String dni, Usuario u){
        try{

            List<PersonaJuridica> personaJuridicaList = gestionClientes.getPersonasJuridicas();
            for (PersonaJuridica pj : personaJuridicaList){
                if (pj.getIdentificacion().equals(dni)){
                    personaJuridica = pj;
                }
            }

            gestionAutorizados.anadirAutorizados(sesion.getUsuario(), personaJuridica, au);
            return "panelAdmin.xhtml";
        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        } catch (PersonaJuridicaNoEncontrada personaJuridicaNoEncontrada) {
            personaJuridicaNoEncontrada.printStackTrace();
        }
        return null;
    }

    public String darBajar(Long id){

        try{
            gestionAutorizados.eliminarAutorizados(sesion.getUsuario(), id);
            return "panelAdmin.xhtml";
        } catch (AutorizadoNoEncontradoException e) {
            e.printStackTrace();
        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        }


        return null;
    }

    public String modifcicar(Autorizado autorizado1) throws UsuarioNoEncontrado {
        this.autorizado = autorizado1;
       return "modficarAutorizado.xhtml";
    }

    public String accion(){
        try{
            gestionAutorizados.modificarAutorizados(sesion.getUsuario(), autorizado, autorizado.getId());
            return "autorizados.xhtml";
        } catch (AutorizadoNoEncontradoException e) {
            e.printStackTrace();
        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        } catch (ModificarAutorizadosDistintaID modificarAutorizadosDistintaID) {
            modificarAutorizadosDistintaID.printStackTrace();
        }
        return null;
    }

    public String accionNuevoAu(){
        return "nuevoAutorizado.xhtml";
    }

    public String nuevoAutorizado (){
        try{
            if (!nuevo.getContraseña().equals(repass)) {
                FacesMessage fm = new FacesMessage("Las contraseñas deben coincidir");
                FacesContext.getCurrentInstance().addMessage("registro:repass", fm);
                return null;
            }
            nuevo.setAutorizado(autorizado);
            nuevo.setAdministrativo(false);
            gestionAutorizados.nuevoAutorizado(sesion.getUsuario(), autorizado, id, nuevo);
            return "panelAdmin.xhtml";
        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        } catch (PersonaJuridicaNoEncontrada personaJuridicaNoEncontrada) {
            personaJuridicaNoEncontrada.printStackTrace();
        } catch (UsuarioNombreRepetido usuarioNombreRepetido) {
            usuarioNombreRepetido.printStackTrace();
        }

        return null;
    }

}
