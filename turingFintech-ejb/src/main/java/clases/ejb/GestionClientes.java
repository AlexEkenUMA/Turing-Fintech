package clases.ejb;

import clases.ejb.exceptions.ClienteNoEncontradoException;
import clases.ejb.exceptions.ClienteNoValidoException;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.Cliente;
import es.uma.turingFintech.PersonaFisica;
import es.uma.turingFintech.PersonaJuridica;

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
    public void modificarCliente(Cliente c, String ID) throws ClienteNoEncontradoException;


    /**

     Este método permite a un administrativo dar de baja a un cliente
     RF4
     */
    public void eliminarCliente (Cliente c, String ID) throws ClienteNoEncontradoException;


    public void darAlta2 (Long id, String tipoCliente, String RazonSocial, String nombre, String apellidos,
                          Date fechaNac, String direccion, int codigoPostal,
                          String pais, List<Autorizado> au, String ciudad) throws ClienteNoValidoException;


    public Cliente getCliente (Long id);


    public List<PersonaFisica> getPersonasFisicas ();

    public List<PersonaJuridica> getPersonasJuridicas();



    }
