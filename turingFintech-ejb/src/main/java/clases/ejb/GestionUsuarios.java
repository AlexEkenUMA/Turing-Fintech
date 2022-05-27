package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.PersonaFisica;
import es.uma.turingFintech.Usuario;

import javax.ejb.Local;

@Local
public interface GestionUsuarios {

    public boolean usuarioCorrecto (Usuario u) throws AutorizadoBloqueado, AutorizadoSoloTieneAccesoACuentasClienteBloqueado, PersonaFisicaBloqueada, UsuarioNoEncontrado, EmpresaNoTieneAcceso;

    public boolean usuarioAdministrativo (Usuario u) throws UsuarioNoEncontrado, NoEsAdministrativo;

    public Usuario refrescarUsuario(Usuario u) throws AutorizadoBloqueado, UsuarioNoEncontrado, EmpresaNoTieneAcceso, AutorizadoSoloTieneAccesoACuentasClienteBloqueado, PersonaFisicaBloqueada;

    public Usuario refrescarUsuarioAdmin(Usuario u) throws AutorizadoBloqueado, UsuarioNoEncontrado, EmpresaNoTieneAcceso, AutorizadoSoloTieneAccesoACuentasClienteBloqueado, PersonaFisicaBloqueada, NoEsAdministrativo;

    public void nuevoUsuario(Usuario u, String nombre, String contrasena, Boolean admin, Autorizado autorizado) throws UsuarioNombreRepetido, UsuarioNoEncontrado, NoEsAdministrativo;
}
