package es.uma.turingFintech.backing;

import clases.ejb.GestionClientes;
import clases.ejb.exceptions.ClienteNoValidoException;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.PersonaFisica;
import es.uma.turingFintech.PersonaJuridica;
import es.uma.turingFintech.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "darAltaJuridico")
@RequestScoped
public class darAltaClienteJuridico {


    @Inject
    private GestionClientes gestionClientes;

    @Inject
    private InfoSesion sesion;

    private PersonaJuridica personaJuridica;

    private Usuario usuario;

    private boolean darAltaOk;

    public darAltaClienteJuridico(){
        personaJuridica = new PersonaJuridica();
        darAltaOk = false;
    }


    public PersonaJuridica getPersonaJuridica (){
        return personaJuridica;
    }

    public void setPersonaJuridica (PersonaJuridica j){
        this.personaJuridica = j;
    }

    public boolean isDarAltaOk() {
        return darAltaOk;
    }

    public String darAlta(){
        try{
            List<Autorizado> au = new ArrayList<>();
            gestionClientes.darDeAltaCliente(sesion.getUsuario(), personaJuridica.getIdentificacion(),"Juridico", personaJuridica.getRazon_Social(),
                    null,null,null,personaJuridica.getDireccion(), personaJuridica.getCodigo_Postal(), personaJuridica.getPais(), au,
                    personaJuridica.getCiudad());
            darAltaOk = true;
            return "darAltaExito.xhtml";

        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (ClienteNoValidoException e) {
            e.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        }

        return null;
    }
}
