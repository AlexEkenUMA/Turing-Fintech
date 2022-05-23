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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public void darDeAltaCliente (Usuario u, String id, String tipoCliente, String RazonSocial, String nombre, String apellidos,
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
    public void modificarCliente(Usuario u, Cliente c, String ID) throws TipoNoValidoException, ModificarClienteDistintaID, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo {


        //gestionUsuarios.usuarioAdministrativo(u);

        if(c.getTipo_Cliente().equals("Juridico")){
            PersonaJuridica personaJuridicaExiste = null;
            List<PersonaJuridica> empresas = getPersonasJuridicas();
            for(PersonaJuridica pj: empresas){
                if(ID.equals(pj.getIdentificacion())){
                    personaJuridicaExiste = pj;
                }
            }
            if(personaJuridicaExiste == null){
                throw new ClienteNoEncontradoException();
            }
            if(c.getId().equals(personaJuridicaExiste.getId()) && c.getIdentificacion().equals(personaJuridicaExiste.getIdentificacion())){
                em.merge(c);
            }else{
                throw new ModificarClienteDistintaID();
            }
        }
        else if (c.getTipo_Cliente().equals("Fisico")){
            PersonaFisica personaFisicaExiste = null;
            List<PersonaFisica> personasFisicas = getPersonasFisicas();
            for(PersonaFisica pf: personasFisicas){
                if(ID.equals(pf.getIdentificacion())){
                    personaFisicaExiste = pf;
                }
            }
            if(personaFisicaExiste == null){
                throw new ClienteNoEncontradoException();
            }
            if(c.getId().equals(personaFisicaExiste.getId()) && c.getIdentificacion().equals(personaFisicaExiste.getIdentificacion())){
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
    public void eliminarCliente (Usuario u, String ID) throws CuentaActiva, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo {
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
                        if(cf.getEstado().equals("Activa")) {
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
                        if(cf.getEstado().equals("Activa")) {
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

    @Override
    public void bloquearCliente (Usuario u, Cliente c) throws BloquearClienteYaBloqueado, TipoNoValidoException, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo{
        gestionUsuarios.usuarioAdministrativo(u);
        if(c.getTipo_Cliente().equals("Juridico")){
            PersonaJuridica pj = em.find(PersonaJuridica.class, c.getId());
            if(pj == null){
                throw new ClienteNoEncontradoException();
            }
            if(pj.getEstado().equals("Bloqueado")){
                throw new BloquearClienteYaBloqueado();
            }
            else{
                pj.setEstado("Bloqueado");
                em.merge(pj);
            }
        }
        else if(c.getTipo_Cliente().equals("Fisica")){
            PersonaFisica pf = em.find(PersonaFisica.class, c.getId());
            if(pf == null){
                throw new ClienteNoEncontradoException();
            }
            if(pf.getEstado().equals("Bloqueado")){
                throw new BloquearClienteYaBloqueado();
            }
            else{
                pf.setEstado("Bloqueado");
                em.merge(pf);
            }
        }
        else{
            throw new TipoNoValidoException();
        }
    }

    @Override
    public void desbloquearCliente (Usuario u, Cliente c) throws DesbloquearClienteQueNoEstaBloqueado, TipoNoValidoException, ClienteNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo{
        gestionUsuarios.usuarioAdministrativo(u);
        if(c.getTipo_Cliente().equals("Juridico")){
            PersonaJuridica pj = em.find(PersonaJuridica.class, c.getId());
            if(pj == null){
                throw new ClienteNoEncontradoException();
            }
            if(!pj.getEstado().equals("Bloqueado")){
                throw new DesbloquearClienteQueNoEstaBloqueado();
            }
            else{
                pj.setEstado("Activo");
                em.merge(pj);
            }
        }
        else if (c.getTipo_Cliente().equals("Fisica")){
            PersonaFisica pf = em.find(PersonaFisica.class, c.getId());
            if(pf == null){
                throw new ClienteNoEncontradoException();
            }
            if(!pf.getEstado().equals("Bloqueado")){
                throw new DesbloquearClienteQueNoEstaBloqueado();
            }
            else{
                pf.setEstado("Activo");
                em.merge(pf);
            }
        }
        else{
            throw new TipoNoValidoException();
        }
    }

    public List<Cliente> getClientes(){
        Query query = em.createQuery("select cliente from Cliente cliente");
        List<Cliente> clientes = (List<Cliente>) query.getResultList();
        return clientes;
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
    public List<Cliente> getClientesHolanda(Usuario u, String dni, String nombre, String apellidos, String direccion)
            throws NoEsAdministrativo, UsuarioNoEncontrado, NingunClienteCoincideConLosParametrosDeBusqueda{
        gestionUsuarios.usuarioAdministrativo(u);
        List<Cliente> resultados;

        if(nombre != null || apellidos != null){
            System.out.println("HOLAA");
            //se esta buscando persona fisica
            Query query = em.createQuery("Select pf from PersonaFisica pf " +
                    "where pf.identificacion like :dni " +
                    "and pf.Nombre = :nombre and pf.Apellidos = :apellidos " +
                    "and pf.direccion like :direccion ");
            if (dni != null){
                query.setParameter("dni", dni);
            }else{
                query.setParameter("dni", "%");
            }
            if (nombre != null){
                System.out.println(nombre);
                query.setParameter("nombre", nombre);
            }else{
                query.setParameter("nombre", "%");
            }
            if (apellidos != null){
                query.setParameter("apellidos", apellidos);
            }else{
                query.setParameter("apellidos", "%");
            }
            if (direccion != null){
                query.setParameter("direccion", direccion);
            }else{
                query.setParameter("direccion", "%");
            }
            resultados = query.getResultList();
        }
        else{
            //se esta buscando persona juridica o fisica
            Query query = em.createQuery("Select c from Cliente c " +
                    "where c.identificacion like :dni " +
                    "and c.direccion like :direccion ");
            if (dni != null){
                query.setParameter("dni", dni);
            }else{
                query.setParameter("dni", "%");
            }
            if (direccion != null){
                query.setParameter("direccion", direccion);
            }else{
                query.setParameter("direccion", "%");
            }
            resultados = query.getResultList();
        }
        if(resultados.isEmpty()){
            throw new NingunClienteCoincideConLosParametrosDeBusqueda();
        }
        else{
            for(Cliente c : resultados){
                if(c instanceof PersonaJuridica){
                    PersonaJuridica pj = (PersonaJuridica) c;
                    for(Autorizado au : pj.getAutorizados()){
                        if(au.getUsuario().getCliente() != null){
                            resultados.add(au.getUsuario().getCliente());
                        }
                    }
                }
            }
        }

        return resultados;
    }
}
