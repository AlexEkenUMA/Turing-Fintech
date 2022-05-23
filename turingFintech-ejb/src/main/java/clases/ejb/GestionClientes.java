package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface GestionClientes {


    public void darDeAltaCliente (Usuario u, String id, String tipoCliente, String RazonSocial, String nombre, String apellidos,
                                  Date fechaNac, String direccion, int codigoPostal,
                                  String pais, List<Autorizado> au, String ciudad) throws ClienteNoValidoException, UsuarioNoEncontrado, NoEsAdministrativo;

    /**

     Este método permite a un administrativo modificar los datos de un cliente
     RF3
     */
    public void modificarCliente(Usuario u, Cliente c, String ID) throws TipoNoValidoException, ModificarClienteDistintaID, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;


    /**

     Este método permite a un administrativo dar de baja a un cliente
     RF4
     */
    public void eliminarCliente (Usuario u, String ID) throws CuentaActiva, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;


    public void bloquearCliente (Usuario u, Cliente c) throws BloquearClienteYaBloqueado, TipoNoValidoException, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;

    public void desbloquearCliente (Usuario u, Cliente c) throws DesbloquearClienteQueNoEstaBloqueado, TipoNoValidoException, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;

    public List<Cliente> getClientes();

    public List<PersonaFisica> getPersonasFisicas ();

    public List<PersonaJuridica> getPersonasJuridicas();

    public List<Cliente> getClientesHolanda(Usuario u, String dni, String nombre, String apellido, String direccion)
            throws  NoEsAdministrativo, UsuarioNoEncontrado, NingunClienteCoincideConLosParametrosDeBusqueda;

    }
