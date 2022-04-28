package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.PersonaJuridica;
import es.uma.turingFintech.Usuario;

import javax.ejb.Local;

@Local
public interface    GestionAutorizados {

    public void anadirAutorizados (Usuario u, PersonaJuridica pj, Autorizado autorizado) throws NoEsAdministrativo,
            PersonaJuridicaNoEncontrada, UsuarioNoEncontrado;

    public void modificarAutorizados(Usuario u, Autorizado au, Long ID) throws UsuarioNoEncontrado, NoEsAdministrativo, AutorizadoNoEncontradoException, ModificarAutorizadosDistintaID;


    public void eliminarAutorizados(Usuario u, Long ID) throws UsuarioNoEncontrado, NoEsAdministrativo, AutorizadoNoEncontradoException;


}
