package clases.ejb;

import clases.ejb.exceptions.ClienteNoEncontradoException;
import clases.ejb.exceptions.ClienteNoValidoException;
import es.uma.turingFintech.Cliente;
import es.uma.turingFintech.PersonaFisica;
import es.uma.turingFintech.PersonaJuridica;
import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.logging.Logger;

@Stateless
public class ClientesEJB implements GestionClientes {

    private static final Logger LOG = Logger.getLogger(ClientesEJB.class.getCanonicalName());

    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;



    @Override
    public void darAltaCliente (Cliente c, String tipoCliente, String razonSocial, String nombre, String apellidos, Date fecha_nacimiento) throws ClienteNoValidoException{
        if(tipoCliente.equals("Juridico")){
            Cliente juridico = new PersonaJuridica(c.getId(),c.getIdentificacion(),c.getTipo_Cliente(),c.getEstado(),
            c.getFecha_Alta(),c.getFecha_Baja(),c.getDireccion(),c.getCiudad(),c.getCodigo_Postal(),c.getPais(),razonSocial);
            em.persist(juridico);
        }else if(tipoCliente.equals("Fisica")){
            Cliente fisico = new PersonaFisica(c.getId(),c.getIdentificacion(),c.getTipo_Cliente(),c.getEstado(),
                    c.getFecha_Alta(),c.getFecha_Baja(),c.getDireccion(),c.getCiudad(),c.getCodigo_Postal(),c.getPais(),nombre,apellidos,fecha_nacimiento);
            em.persist(fisico);
        }else{
            throw new ClienteNoValidoException();
        }


    }

    @Override
    public void modificarCliente(Cliente c, String ID) throws ClienteNoEncontradoException {
        Cliente clienteExiste = em.find(Cliente.class, ID);
        if(clienteExiste == null){
            throw new ClienteNoEncontradoException();
        }
        em.merge(c);
    }


    @Override
    public void eliminarCliente (Cliente c){

        //TODO


    }


}
