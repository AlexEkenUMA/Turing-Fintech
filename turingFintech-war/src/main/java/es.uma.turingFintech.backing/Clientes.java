package es.uma.turingFintech.backing;

import clases.ejb.GestionClientes;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "clientes")
@RequestScoped
public class Clientes {

    public static enum Modo {
        MODIFICAR,
        INSERTAR,
        NOACCION,
        FISICA,
        JURIDICA
    };


    @Inject
    private GestionClientes gestionClientes;

    @Inject
    private InfoSesion sesion;

    private Modo modo;

    private Cliente cliente;

    private PersonaFisica personaFisica;
    private PersonaJuridica personaJuridica;

    private Usuario usuario;

    private String id;

    private boolean modificarOk;

    public Clientes(){
        cliente         = new Cliente();
        personaFisica   = new PersonaFisica();
        personaJuridica = new PersonaJuridica();
        modificarOk = false;
    }

    public PersonaFisica getPersonaFisica() {return personaFisica;}

    public void setPersonaFisica(PersonaFisica personaFisica) {this.personaFisica = personaFisica;}

    public PersonaJuridica getPersonaJuridica() {return personaJuridica;}

    public void setPersonaJuridica(PersonaJuridica personaJuridica) {this.personaJuridica = personaJuridica;}

    public Cliente getCliente() {return cliente;}

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

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
            return "clientes.xhtml";

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

    public String darAltaJuridica (){
        modo = Modo.JURIDICA;

        return "darAltaClienteJuridico.xhtml";
    }


    public String darAltaFisica (){
        modo = Modo.FISICA;

        return "darAltaClienteIndividual.xhtml";
    }


    public String ejecutarAccion () {


        try {
            switch (modo) {
                case MODIFICAR:
                    gestionClientes.modificarCliente(usuario, cliente, cliente.getIdentificacion());
                    break;
                case JURIDICA:
                    List<Autorizado> au = new ArrayList<>();
                    gestionClientes.darDeAltaCliente(sesion.getUsuario(), personaJuridica.getIdentificacion(),"Juridico", personaJuridica.getRazon_Social(),
                            null,null,null,personaJuridica.getDireccion(), personaJuridica.getCodigo_Postal(), personaJuridica.getPais(), au,
                            personaJuridica.getCiudad());
                    break;

                case FISICA:
                    gestionClientes.darDeAltaCliente(sesion.getUsuario(), personaFisica.getIdentificacion(),"Fisica", null,
                            personaFisica.getNombre(), personaFisica.getApellidos(), personaFisica.getFecha_Nacimiento(),
                            personaFisica.getDireccion(), personaFisica.getCodigo_Postal(), personaFisica.getPais(), null,
                            personaFisica.getCiudad());
            }

            return "clientes.xhtml";

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
        } catch (ClienteNoValidoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
