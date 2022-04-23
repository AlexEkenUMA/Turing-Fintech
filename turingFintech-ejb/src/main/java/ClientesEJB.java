import es.uma.turingFintech.Cliente;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

@Stateless
public class ClientesEJB implements GestionClientes{

    private static final Logger LOG = Logger.getLogger(ClientesEJB.class.getCanonicalName());

    @PersistenceContext(name = "turingFintech")
    private EntityManager em;



    @Override

    public void darAltaCliente (Cliente c){

        //TODO



    }

    @Override
    public void modificarCliente(Cliente c){

        //TODO

    }


    @Override
    public void eliminarCliente (Cliente c){

        //TODO


    }


}
