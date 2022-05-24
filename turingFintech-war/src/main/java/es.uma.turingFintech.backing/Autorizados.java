package es.uma.turingFintech.backing;

import clases.ejb.GestionAutorizados;
import clases.ejb.GestionClientes;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.PersonaJuridicaNoEncontrada;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.PersonaJuridica;
import es.uma.turingFintech.Usuario;

import javax.enterprise.context.RequestScoped;
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

    String dni= "";

    private PersonaJuridica personaJuridica;

    private Usuario usuario;

    public Autorizados(){
        personaJuridica = new PersonaJuridica();
    }

    public PersonaJuridica getPersonaJuridica() {
        return personaJuridica;
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

    public String anadirAuto(PersonaJuridica pj, Usuario u){
        personaJuridica = pj;
        usuario = u;
        dni = pj.getIdentificacion();
        sesion.refrescarUsuarioAdmin();
        return "listaAutorizados.xhtml";
    }

    public String seleccion (Autorizado au, String dni, Usuario u){
        try{
            sesion.setUsuario(u);
            sesion.refrescarUsuarioAdmin();
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

}
