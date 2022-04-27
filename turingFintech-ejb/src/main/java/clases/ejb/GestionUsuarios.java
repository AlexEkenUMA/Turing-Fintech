package clases.ejb;

import clases.ejb.exceptions.EmpresaNoTieneAcceso;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.Usuario;

import javax.ejb.Local;

@Local
public interface GestionUsuarios {

    public boolean usuarioCorrecto (Usuario u) throws UsuarioNoEncontrado, EmpresaNoTieneAcceso;

    public boolean usuarioAdministrativo (Usuario u) throws UsuarioNoEncontrado, NoEsAdministrativo;


}
