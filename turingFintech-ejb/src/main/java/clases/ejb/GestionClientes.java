package clases.ejb;

import es.uma.turingFintech.Cliente;

import javax.ejb.Local;

@Local
public interface GestionClientes {

    /**

     Este método es para insertar un cliente en la base de datos
     Se le permite a un administrativo
     RF2
    */
    public void darAltaCliente (Cliente c,String tipoCliente, String razonSocial);


    /**

     Este método permite a un administrativo modificar los datos de un cliente
     RF3
     */
    public void modificarCliente(Cliente c);


    /**

     Este método permite a un administrativo dar de baja a un cliente
     RF4
     */
    public void eliminarCliente (Cliente c);



}
