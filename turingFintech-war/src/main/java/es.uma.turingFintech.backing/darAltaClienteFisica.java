package es.uma.turingFintech.backing;

import clases.ejb.GestionClientes;
import clases.ejb.exceptions.ClienteNoValidoException;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.PersonaFisica;
import es.uma.turingFintech.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "darAltaClienteFisica")
@RequestScoped
public class darAltaClienteFisica {

    @Inject
    private GestionClientes gestionClientes;

    @Inject
    private InfoSesion sesion;

    private PersonaFisica personaFisica;

    private Usuario usuario;

    private boolean darAltaOk;

    public darAltaClienteFisica(){
         personaFisica = new PersonaFisica();
         darAltaOk = false;
    }


    public PersonaFisica getPersonaFisica (){
        return personaFisica;
    }

    public void setPersonaFisica (PersonaFisica f){
        this.personaFisica = f;
    }

    public boolean isDarAltaOk() {
        return darAltaOk;
    }

    public String darAlta(){
        try{
            gestionClientes.darDeAltaCliente(sesion.getUsuario(), personaFisica.getIdentificacion(),"Fisica", null,
                    personaFisica.getNombre(), personaFisica.getApellidos(), personaFisica.getFecha_Nacimiento(),
                    personaFisica.getDireccion(), personaFisica.getCodigo_Postal(), personaFisica.getPais(), null,
                    personaFisica.getCiudad());
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
