package es.uma.turingFintech.backing;

import clases.ejb.GestionClientes;
import es.uma.turingFintech.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "PanelAdministrativo")
@RequestScoped
public class PanelAdministrativo {

    @Inject
   private  GestionClientes gestionClientes;

    @Inject
    private InfoSesion sesion;
    private Usuario usuario;





}
