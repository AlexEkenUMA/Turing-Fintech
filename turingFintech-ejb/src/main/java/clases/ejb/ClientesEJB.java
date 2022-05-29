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

    @PersistenceContext(unitName = "turingFintech-ejb")
    private EntityManager em;

    @EJB
    GestionUsuarios gestionUsuarios;


    @Override
    public void darDeAltaCliente (Usuario u, String id, String tipoCliente, String RazonSocial, String nombre, String apellidos,
                          Date fechaNac, String direccion, int codigoPostal, String pais, List<Autorizado> au, String ciudad)
            throws ClienteNoValidoException, UsuarioNoEncontrado, NoEsAdministrativo {

        gestionUsuarios.usuarioAdministrativo(u);



        if (!tipoCliente.equals("Juridico") && !tipoCliente.equals("Fisico")){
            throw new ClienteNoValidoException();
        }
        Date date = new Date();
        if (tipoCliente.equals("Juridico")){

            PersonaJuridica personaJuridica = new PersonaJuridica (null, id, tipoCliente, "Activo", date, null,
                    direccion,ciudad, codigoPostal, pais, RazonSocial);
            personaJuridica.setAutorizados(au);

            em.persist(personaJuridica);
        }
        if (tipoCliente.equals("Fisico")){
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
        else if(c.getTipo_Cliente().equals("Fisico")){
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
        else if (c.getTipo_Cliente().equals("Fisico")){
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

    @Override
    public void modificarJuridico (Usuario u, PersonaJuridica pj) throws UsuarioNoEncontrado, NoEsAdministrativo, ClienteNoEncontradoException {
        gestionUsuarios.usuarioAdministrativo(u);
        PersonaJuridica personaJuridica = em.find(PersonaJuridica.class, pj.getId());
        if(personaJuridica == null){
            throw new ClienteNoEncontradoException();
        }
        em.merge(pj);
    }

    @Override
    public void modificarFisico (Usuario u, PersonaFisica pf) throws UsuarioNoEncontrado, NoEsAdministrativo, ClienteNoEncontradoException {
        gestionUsuarios.usuarioAdministrativo(u);
        PersonaFisica personaFisica = em.find(PersonaFisica.class, pf.getId());
        if(personaFisica == null){
            throw new ClienteNoEncontradoException();
        }
        em.merge(pf);
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

    public PersonaJuridica getPersonasJuridicaID(Long id) throws PersonaJuridicaNoEncontrada {
        Query query = em.createQuery("select cliente from PersonaJuridica cliente where cliente.id = :id");
        query.setParameter("id",id);
        List<PersonaJuridica> personaJuridicas = (List<PersonaJuridica>) query.getResultList();
        if (personaJuridicas.isEmpty()){
            throw new PersonaJuridicaNoEncontrada();
        }
        return personaJuridicas.get(0);
    }

    public PersonaFisica getPersonasFisicaID(Long id) throws ClienteNoEncontradoException {
        Query query = em.createQuery("select cliente from PersonaFisica cliente where cliente.id = :id");
        query.setParameter("id",id);
        List<PersonaFisica> personaFisicas = (List<PersonaFisica>) query.getResultList();
        if (personaFisicas.isEmpty()){
            throw new ClienteNoEncontradoException();
        }
        return personaFisicas.get(0);
    }

    public List<PersonaJuridica> getPersonasJuridicas(){
        Query query = em.createQuery("select cliente from PersonaJuridica cliente");
        List<PersonaJuridica> personaJuridicas = (List<PersonaJuridica>) query.getResultList();
        return personaJuridicas;
    }

    @Override
    public List<Cliente> getClientesHolanda(String nombre, String apellidos, String startPeriod, String endPeriod)
            throws NoEsAdministrativo, UsuarioNoEncontrado, NingunClienteCoincideConLosParametrosDeBusqueda{
        List<Cliente> resultados;

        if(nombre != null || apellidos != null){
            //se esta buscando persona fisica
            Query query = em.createQuery("Select pf from PersonaFisica pf " +
                    "where pf.Nombre like :nombre and pf.Apellidos like :apellidos ");
            if (nombre != null && !nombre.isEmpty()){
                query.setParameter("nombre", nombre);
            }else{
                query.setParameter("nombre", "%");
            }
            if (apellidos != null && !apellidos.isEmpty()){
                query.setParameter("apellidos", apellidos);
            }else{
                query.setParameter("apellidos", "%");
            }
            resultados = query.getResultList();
        }
        else{
            //se esta buscando persona juridica o fisica
            Query query = em.createQuery("Select c from Cliente c ");
            resultados = query.getResultList();
        }

        if(resultados.isEmpty()){
            throw new NingunClienteCoincideConLosParametrosDeBusqueda();
        }
        else{
            List<Cliente> autorizadosClientes = new ArrayList<>();
            for(Cliente c : resultados){
                if(c instanceof PersonaJuridica){
                    PersonaJuridica pj = (PersonaJuridica) c;
                    for(Autorizado au : pj.getAutorizados()){
                        if(au.getUsuario().getCliente() != null){
                            autorizadosClientes.add(au.getUsuario().getCliente());
                        }
                    }
                }
            }
            resultados.addAll(autorizadosClientes);
        }
        //AQUI HABRIA QUE MIRAR SI ESTA DENTRO DEL RANGO DE FECHAS

        return resultados;
    }
}
