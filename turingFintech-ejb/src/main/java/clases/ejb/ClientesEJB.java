package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;
import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class ClientesEJB implements GestionClientes {

    private static final Logger LOG = Logger.getLogger(ClientesEJB.class.getCanonicalName());

    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;

    @EJB
    GestionUsuarios gestionUsuarios;


    @Override
    public void darAlta2 (Usuario u, Long id, String tipoCliente, String RazonSocial, String nombre, String apellidos,
                          Date fechaNac, String direccion, int codigoPostal, String pais, List<Autorizado> au, String ciudad)
            throws ClienteNoValidoException, UsuarioNoEncontrado, NoEsAdministrativo {

        gestionUsuarios.usuarioAdministrativo(u);

        if (!tipoCliente.equals("Juridico") && !tipoCliente.equals("Fisica")){
            throw new ClienteNoValidoException();
        }
        Date date = new Date();
        if (tipoCliente.equals("Juridico")){

            PersonaJuridica personaJuridica = new PersonaJuridica (null, id, tipoCliente, "Activo", date, null,
                    direccion,ciudad, codigoPostal, pais, RazonSocial);
            personaJuridica.setAutorizados(au);

            em.persist(personaJuridica);
        }
        if (tipoCliente.equals("Fisica")){
            PersonaFisica personaFisica = new PersonaFisica(null, id, tipoCliente, "Activo", date, null, direccion,ciudad,
                    codigoPostal, pais, nombre, apellidos, fechaNac);


            em.persist(personaFisica);
        }
    }


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
    public void modificarCliente(Usuario u, Cliente c, String ID) throws ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo {

        gestionUsuarios.usuarioAdministrativo(u);

        Cliente clienteExiste = em.find(Cliente.class, ID);
        if(clienteExiste == null){
            throw new ClienteNoEncontradoException();
        }
        em.merge(c);
    }


    @Override
    public void eliminarCliente (Usuario u, Cliente c, String ID) throws CuentaActiva, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo {

        gestionUsuarios.usuarioAdministrativo(u);
        Date fecha = new Date();

        Cliente cliente = em.find(Cliente.class, ID);
        if(cliente == null){
            throw new ClienteNoEncontradoException();
        }

        List<CuentaFintech> cuentas = cliente.getCuentasFintech();
        boolean ok = true;
        for(CuentaFintech cf : cuentas){
            if(cf.isEstado()) {
                ok = false;
            }
        }
        if(ok){
          cliente.setEstado("Baja");
          cliente.setFecha_Baja(fecha);
          em.merge(cliente);
        }else{
            throw new CuentaActiva();
        }

    }


    public Cliente getCliente (Long id){
       return em.find(Cliente.class, id);
    }

    public List<PersonaFisica> getPersonasFisicas (){
        Query query = em.createQuery("select cliente from PersonaFisica cliente");
        List<PersonaFisica> personaFisicas = (List<PersonaFisica>) query.getResultList();
        return personaFisicas;
    }

    public List<PersonaJuridica> getPersonasJuridicas(){
        Query query = em.createQuery("select cliente from PersonaJuridica cliente");
        List<PersonaJuridica> personaJuridicas = (List<PersonaJuridica>) query.getResultList();
        return personaJuridicas;
    }
}
