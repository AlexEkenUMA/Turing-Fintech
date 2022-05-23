package es.uma.turingFintech.backing;

import clases.ejb.GestionClientes;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.Cliente;
import es.uma.turingFintech.PersonaFisica;
import es.uma.turingFintech.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "modificarCliente")
@RequestScoped
public class modificarCliente {

    @Inject
    private GestionClientes gestionClientes;

    @Inject
    private InfoSesion sesion;

    private Cliente cliente;

    private Usuario usuario;

    private String id;

    private boolean modificarOk;

    public modificarCliente(){
        cliente     = new Cliente();
        modificarOk = false;
    }

    public Cliente getCliente() {return cliente;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public boolean isModificarOk() {return modificarOk;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String modificar(Cliente c){
        this.cliente = c;
        System.out.println(c.toString());
        return "modificar.xhtml";
    }

    public String ejecutarAccion (){

        try{
            gestionClientes.modificarCliente(usuario,cliente, cliente.getIdentificacion());
            sesion.refrescarUsuarioAdmin();
            return "modificarCliente.xhtml";
        } catch (ModificarClienteDistintaID modificarClienteDistintaID) {
            modificarClienteDistintaID.printStackTrace();
        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (TipoNoValidoException e) {
            e.printStackTrace();
        } catch (ClienteNoEncontradoException e) {
            e.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        }
        return null;

    }


}
