package clases.ejb;

import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.PersonaJuridicaNoEncontrada;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.PersonaJuridica;
import es.uma.turingFintech.Usuario;

import javax.ejb.Local;

@Local
public interface GestionAutorizados {

    public void anadirAutorizados (Usuario u, PersonaJuridica pj) throws NoEsAdministrativo,
            PersonaJuridicaNoEncontrada, UsuarioNoEncontrado;





}
