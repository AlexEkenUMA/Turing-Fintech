package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AutorizadosEJB implements GestionAutorizados{
    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;

    @EJB GestionUsuarios gestionUsuarios;

    @Override
    public void anadirAutorizados (Usuario u, PersonaJuridica pj, Autorizado autorizado) throws NoEsAdministrativo,
            UsuarioNoEncontrado, PersonaJuridicaNoEncontrada{

        gestionUsuarios.usuarioAdministrativo(u);

        PersonaJuridica personaJuridica = em.find(PersonaJuridica.class, pj.getId());
        if (personaJuridica == null){
            throw new PersonaJuridicaNoEncontrada();
        }
        Autorizado autporizadoEntity = em.find(Autorizado.class, autorizado.getId());
        if (autporizadoEntity == null){
            em.persist(autorizado);
        }
        List<Autorizado> autorizadoList = pj.getAutorizados();
        autorizadoList.add(autorizado);
        pj.setAutorizados(autorizadoList);
        List<PersonaJuridica> personaJuridicas = autorizado.getEmpresas();
        personaJuridicas.add(pj);
        autorizado.setEmpresas(personaJuridicas);
        em.merge(autorizado);
        em.merge(pj);
    }

    @Override
    public void modificarAutorizados(Usuario u, Autorizado au, Long ID) throws UsuarioNoEncontrado, NoEsAdministrativo, AutorizadoNoEncontradoException, ModificarAutorizadosDistintaID {

        gestionUsuarios.usuarioAdministrativo(u);

        Autorizado autorizadoExiste = em.find(Autorizado.class, au.getId());
        if(autorizadoExiste == null){
            throw new AutorizadoNoEncontradoException();
        }

        if(ID != au.getId()){
            throw new ModificarAutorizadosDistintaID();
        }else{
            em.merge(au);
        }
    }

    @Override
    public void eliminarAutorizados(Usuario u, Long ID)
            throws UsuarioNoEncontrado, NoEsAdministrativo, AutorizadoNoEncontradoException{

        gestionUsuarios.usuarioAdministrativo(u);

        Autorizado autorizadoExiste = em.find(Autorizado.class, ID);
        if(autorizadoExiste == null){
            throw new AutorizadoNoEncontradoException();
        }
            autorizadoExiste.setEstado("Baja");
            List<Autorizado> sinautorizados = new ArrayList<>();
            List<PersonaJuridica> listaempresas = autorizadoExiste.getEmpresas();
            //no sabemos si es necesario
            for (PersonaJuridica pj : listaempresas){
                List<Autorizado> listaAutorizados = pj.getAutorizados();
                listaAutorizados.remove(autorizadoExiste);
                pj.setAutorizados(listaAutorizados);
                em.merge(pj);
            }
            List<PersonaJuridica> vacia = new ArrayList<>();
            autorizadoExiste.setEmpresas(vacia);
            em.merge(autorizadoExiste);
    }

    @Override
    public void bloquearAutorizado (Usuario u, Autorizado au) throws BloquearAutorizadoYaBloqueado, AutorizadoNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo{
        gestionUsuarios.usuarioAdministrativo(u);
        Autorizado autorizado = em.find(Autorizado.class, au.getId());
        if(autorizado == null){
            throw new AutorizadoNoEncontradoException();
        }
        if(autorizado.getEstado().equals("Bloqueado")){
            throw new BloquearAutorizadoYaBloqueado();
        }
        else{
            autorizado.setEstado("Bloqueado");
            em.merge(autorizado);
        }

    }

    @Override
    public void desbloquearAutorizado (Usuario u, Autorizado au) throws DesbloquearAutorizadoQueNoEstaBloqueado, AutorizadoNoEncontradoException, UsuarioNoEncontrado, NoEsAdministrativo{
        gestionUsuarios.usuarioAdministrativo(u);
        Autorizado autorizado = em.find(Autorizado.class, au.getId());
        if(autorizado == null){
            throw new AutorizadoNoEncontradoException();
        }
        if(!autorizado.getEstado().equals("Bloqueado")){
            throw new DesbloquearAutorizadoQueNoEstaBloqueado();
        }
        else{
            autorizado.setEstado("Activo");
            em.merge(autorizado);
        }
    }

    @Override
    public List<Autorizado> getAutorizados(){
        Query query = em.createQuery("select autorizado from Autorizado autorizado");
        List<Autorizado> autorizadoList = (List<Autorizado>) query.getResultList();
        return autorizadoList;
    }

}
