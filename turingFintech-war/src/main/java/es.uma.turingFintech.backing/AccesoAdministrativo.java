package es.uma.turingFintech.backing;

import clases.ejb.GestionUsuarios;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "AccesoAdministrativos")
@RequestScoped
public class AccesoAdministrativo {


    @Inject
    private GestionUsuarios gestionUsuarios;

    @Inject
    private InfoSesion sesion;

    private Usuario usuario;

    public AccesoAdministrativo (){
        usuario = new Usuario();
    }

    public Usuario getUsuario(){
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String entrar () {

        try {
            gestionUsuarios.usuarioAdministrativo(this.usuario);
            sesion.setUsuario(gestionUsuarios.refrescarUsuarioAdmin(usuario));
            return "panelAdmin.xhtml";


        } catch (AutorizadoBloqueado autorizadoBloqueado) {
            autorizadoBloqueado.printStackTrace();
        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (EmpresaNoTieneAcceso empresaNoTieneAcceso) {
            empresaNoTieneAcceso.printStackTrace();
        } catch (AutorizadoSoloTieneAccesoACuentasClienteBloqueado autorizadoSoloTieneAccesoACuentasClienteBloqueado) {
            autorizadoSoloTieneAccesoACuentasClienteBloqueado.printStackTrace();
        } catch (PersonaFisicaBloqueada personaFisicaBloqueada) {
            personaFisicaBloqueada.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        }
        return null;
    }
}

