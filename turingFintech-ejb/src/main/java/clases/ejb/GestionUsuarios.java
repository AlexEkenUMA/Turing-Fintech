package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.PersonaFisica;
import es.uma.turingFintech.Usuario;

import javax.ejb.Local;

@Local
public interface GestionUsuarios {

    public boolean usuarioCorrecto (Usuario u) throws AutorizadoSoloTieneAccesoACuentasClienteBloqueado, PersonaFisicaBloqueada, UsuarioNoEncontrado, EmpresaNoTieneAcceso;

    public boolean usuarioAdministrativo (Usuario u) throws UsuarioNoEncontrado, NoEsAdministrativo;


}
