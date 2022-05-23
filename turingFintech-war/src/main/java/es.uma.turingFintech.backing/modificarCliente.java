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

    public static enum Modo {
        MODIFICAR,
        INSERTAR,
        NOACCION
    };


    @Inject
    private GestionClientes gestionClientes;

    @Inject
    private InfoSesion sesion;

    private Modo modo;

    private Cliente cliente;

    private Usuario usuario;

    private String id;

    private boolean modificarOk;

    public modificarCliente(){
        cliente     = new Cliente();
        modificarOk = false;
    }

    public Cliente getCliente() {return cliente;}

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Modo getModo() {return modo;}

    public void setModo(Modo modo) {this.modo = modo;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public boolean isModificarOk() {return modificarOk;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getAccion() {
        switch (modo) {
            case MODIFICAR:
                return "Modificar";
            case INSERTAR:
                return "Insertar";

        }
        return null;
    }

    public String modificar(Cliente c, Usuario u){
        this.cliente = c;
        this.usuario = u;
        setModo(Modo.MODIFICAR);
        return "modificar.xhtml";
    }

    public String darBaja(String dni, Usuario u){
        try{
            gestionClientes.eliminarCliente(u,dni);
            return "modificarCliente.xhtml";

        } catch (CuentaActiva cuentaActiva) {
            cuentaActiva.printStackTrace();
        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (ClienteNoEncontradoException e) {
            e.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        }
        return null;

    }

    public String ejecutarAccion () {


        try {
            switch (modo) {
                case MODIFICAR:
                    gestionClientes.modificarCliente(usuario, cliente, cliente.getIdentificacion());
                    break;
                case INSERTAR:

                    //gestionClientes.insertar(contacto);
                    break;
            }
           // sesion.refrescarUsuarioAdmin();
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
