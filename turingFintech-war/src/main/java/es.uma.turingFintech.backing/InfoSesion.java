package es.uma.turingFintech.backing;


import clases.ejb.*;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named(value = "infoSesion")
@SessionScoped
public class InfoSesion implements Serializable {

    @Inject
    private GestionUsuarios gestionUsuarios;

    @Inject
    private GestionClientes gestionClientes;

    @Inject
    private GestionCuentas gestionCuentas;

    @Inject
    private GestionAutorizados gestionAutorizados;

    @Inject
    GestionTransacciones gestionTransacciones;

    private Usuario usuario;
    
    /**
     * Creates a new instance of InfoSesion
     */
    public InfoSesion() {
    }

    public synchronized void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public synchronized Usuario getUsuario() {
        return usuario;
    }

    public List<Cliente> getClientes (){
        return gestionClientes.getClientes();
    }

    public List<PersonaJuridica> getJuridica (){return gestionClientes.getPersonasJuridicas();}

    public List<Autorizado> getAutorizado (){return gestionAutorizados.getAutorizados();}

    public List<CuentaFintech> getCuentasFintech(){return gestionCuentas.obtenerCuentasFintech();}

    public List<CuentaFintech> getCuentasCliente() {return gestionCuentas.getCuentasCliente(this.usuario.getCliente().getId());}

    public List<Transaccion> getTransaccionesEmitidad(String iban){return gestionTransacciones.getTransaccionesEmitidad(iban);}



    public synchronized String invalidarSesion()
    {
        if (usuario != null)
        {
            usuario = null;
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }
        return "index.html";
    }


    public synchronized void refrescarUsuario() {
        try {
            if (usuario != null) {
                usuario = gestionUsuarios.refrescarUsuario(usuario);

            }
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
    }

    public synchronized void refrescarUsuarioAdmin() {
        try {
            if (usuario != null) {
                usuario = gestionUsuarios.refrescarUsuarioAdmin(usuario);

            }
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
    }


}
