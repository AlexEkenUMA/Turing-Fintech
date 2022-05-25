package es.uma.turingFintech.backing;


import clases.ejb.GestionUsuarios;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "Acceso")
@RequestScoped
public class Acceso {

    @Inject
    private GestionUsuarios gestionUsuarios;

    @Inject
    private InfoSesion sesion;

    private Usuario usuario;

    public Acceso (){
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
            gestionUsuarios.usuarioCorrecto(this.usuario);
            sesion.setUsuario(gestionUsuarios.refrescarUsuario(usuario));
            return "interfazCliente.xhtml";

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
        }
        return null;

    }


}
