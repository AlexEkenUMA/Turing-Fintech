package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.PersonaJuridica;
import es.uma.turingFintech.Usuario;

import javax.ejb.Local;
import java.util.List;

@Local
public interface    GestionAutorizados {

    public void anadirAutorizados (Usuario u, PersonaJuridica pj, Autorizado autorizado) throws NoEsAdministrativo,
            PersonaJuridicaNoEncontrada, UsuarioNoEncontrado;

    public void modificarAutorizados(Usuario u, Autorizado au, Long ID) throws UsuarioNoEncontrado, NoEsAdministrativo, AutorizadoNoEncontradoException, ModificarAutorizadosDistintaID;


    public void eliminarAutorizados(Usuario u, Long ID) throws UsuarioNoEncontrado, NoEsAdministrativo, AutorizadoNoEncontradoException;

    public void bloquearAutorizado (Usuario u, Autorizado au) throws BloquearAutorizadoYaBloqueado, AutorizadoNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;

    public void desbloquearAutorizado (Usuario u, Autorizado au) throws DesbloquearAutorizadoQueNoEstaBloqueado, AutorizadoNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;


    public List<Autorizado> getAutorizados();

}
