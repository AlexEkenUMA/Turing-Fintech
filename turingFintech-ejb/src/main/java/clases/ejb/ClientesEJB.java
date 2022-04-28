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
    public void modificarCliente(Usuario u, Cliente c, Long ID) throws TipoNoValidoException, ModificarClienteDistintaID, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo {

        gestionUsuarios.usuarioAdministrativo(u);
        if(c.getTipo_Cliente().equals("Juridico")){
            PersonaJuridica personaJuridicaExiste = null;
            List<PersonaJuridica> empresas = getPersonasJuridicas();
            for(PersonaJuridica pj: empresas){
                if(ID == pj.getIdentificacion()){
                    personaJuridicaExiste = pj;
                }
            }
            if(personaJuridicaExiste == null){
                throw new ClienteNoEncontradoException();
            }
            if(c.getId() == personaJuridicaExiste.getId() && c.getIdentificacion() == personaJuridicaExiste.getIdentificacion()){
                em.merge(c);
            }else{
                throw new ModificarClienteDistintaID();
            }
        }
        else if (c.getTipo_Cliente().equals("Fisico")){
            PersonaFisica personaFisicaExiste = null;
            List<PersonaFisica> personasFisicas = getPersonasFisicas();
            for(PersonaFisica pf: personasFisicas){
                if(ID == pf.getIdentificacion()){
                    personaFisicaExiste = pf;
                }
            }
            if(personaFisicaExiste == null){
                throw new ClienteNoEncontradoException();
            }
            if(c.getId() == personaFisicaExiste.getId() && c.getIdentificacion() == personaFisicaExiste.getIdentificacion()){
                em.merge(c);
            }else{
                throw new ModificarClienteDistintaID();
            }
        }
        else{
            throw new TipoNoValidoException();
        }
    }


    @Override
    public void eliminarCliente (Usuario u, Long ID) throws CuentaActiva, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo {
        gestionUsuarios.usuarioAdministrativo(u);
        String tipo = "";
        PersonaJuridica personaJuridicaExiste = null;
        PersonaFisica personaFisicaExiste = null;
        List<PersonaJuridica> empresas = getPersonasJuridicas();
        for(PersonaJuridica pj: empresas){
            if(pj.getIdentificacion().equals(ID)){
                personaJuridicaExiste = pj;
                tipo = "Juridico";
            }
        }
        List<PersonaFisica> personasFisicas = getPersonasFisicas();
        for(PersonaFisica pf: personasFisicas){
            if(pf.getIdentificacion().equals(ID)){
                personaFisicaExiste = pf;
                tipo = "Fisica";
            }
        }
        if(!tipo.equals("Fisica") && !tipo.equals("Juridico")){
            throw new ClienteNoEncontradoException();
        }
        else{
            if(tipo.equals("Juridico")){
                List<CuentaFintech> cuentas = personaJuridicaExiste.getCuentasFintech(); //
                boolean ok = true;
                if(!cuentas.isEmpty()){
                    for(CuentaFintech cf : cuentas){
                        if(cf.isEstado()) {
                            ok = false;
                        }
                    }
                }
                if(ok){
                    personaJuridicaExiste.setEstado("Baja");
                    personaJuridicaExiste.setFecha_Baja(new Date());
                    em.merge(personaJuridicaExiste);
                }else{
                    //el cliente tiene alguna cuenta activa por lo tanto no se le puede dar de baja
                    throw new CuentaActiva();
                }
            }
            if(tipo.equals("Fisica")){
                List<CuentaFintech> cuentas = personaFisicaExiste.getCuentasFintech(); //
                boolean ok = true;
                if(!cuentas.isEmpty()){
                    for(CuentaFintech cf : cuentas){
                        if(cf.isEstado()) {
                            ok = false;
                        }
                    }
                }
                if(ok){
                    personaFisicaExiste.setEstado("Baja");
                    personaFisicaExiste.setFecha_Baja(new Date());
                    em.merge(personaFisicaExiste);
                }else{
                    //el cliente tiene alguna cuenta activa por lo tanto no se le puede dar de baja
                    throw new CuentaActiva();
                }
            }

        }
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

    @Override
    public List<Cliente> getClientesHolanda(){
        Query query = em.createQuery("SELECT c FROM Cliente c");
        //Obtener todos los clientes que han tenido una cuenta segregada durante los 3 ultimos años
        //La información puede ser consultada en cualquier momento de L-V de 7:30 am a 5:30 PM
        List<Cliente> lista = query.getResultList();
        return lista;
    }
}
