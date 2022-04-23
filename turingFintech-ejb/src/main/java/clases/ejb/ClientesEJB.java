package clases.ejb;

import es.uma.turingFintech.Cliente;
import es.uma.turingFintech.PersonaFisica;
import es.uma.turingFintech.PersonaJuridica;
import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Null;
import java.util.logging.Logger;

@Stateless
public class ClientesEJB implements GestionClientes {

    private static final Logger LOG = Logger.getLogger(ClientesEJB.class.getCanonicalName());

    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;



    @Override
    public void darAltaCliente (Cliente c, String tipoCliente, String razonSocial){
        if(tipoCliente.equals("Juridico")){
            Cliente juridico = new PersonaJuridica(c.getId(),c.getIdentificacion(),c.getTipo_Cliente(),c.getEstado(),
            c.getFecha_Alta(),c.getFecha_Baja(),c.getDireccion(),c.getCiudad(),c.getCodigo_Postal(),c.getPais(),razonSocial);
            em.persist(juridico);
        }else if(tipoCliente == "Fisica"){

        }else{

        }


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
