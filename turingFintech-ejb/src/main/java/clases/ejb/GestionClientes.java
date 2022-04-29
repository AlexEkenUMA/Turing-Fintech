package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface GestionClientes {

    /**

     Este método es para insertar un cliente en la base de datos
     Se le permite a un administrativo
     RF2
    */
    public void darAltaCliente (Cliente c,String tipoCliente, String razonSocial, String nombre, String apellidos, Date fecha_nacimiento) throws ClienteNoValidoException;


    /**

     Este método permite a un administrativo modificar los datos de un cliente
     RF3
     */
    public void modificarCliente(Usuario u, Cliente c, Long ID) throws TipoNoValidoException, ModificarClienteDistintaID, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;


    /**

     Este método permite a un administrativo dar de baja a un cliente
     RF4
     */
    public void eliminarCliente (Usuario u, Long ID) throws CuentaActiva, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;


    public void darAlta2 (Usuario u, Long id, String tipoCliente, String RazonSocial, String nombre, String apellidos,
                          Date fechaNac, String direccion, int codigoPostal,
                          String pais, List<Autorizado> au, String ciudad) throws ClienteNoValidoException, UsuarioNoEncontrado, NoEsAdministrativo;

    public void bloquearCliente (Usuario u, Cliente c) throws BloquearClienteYaBloqueado, TipoNoValidoException, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;

    public void desbloquearCliente (Usuario u, Cliente c) throws DesbloquearClienteQueNoEstaBloqueado, TipoNoValidoException, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo;

    public List<PersonaFisica> getPersonasFisicas ();

    public List<PersonaJuridica> getPersonasJuridicas();

    public List<Cliente> getClientesHolanda(Usuario u, Long dni, Date fechaAlta, Date fechaBaja, String nombre, String direccion, String codigoPostal, String pais) throws  NingunClienteCoincideConLosParametrosDeBusqueda;

    }
