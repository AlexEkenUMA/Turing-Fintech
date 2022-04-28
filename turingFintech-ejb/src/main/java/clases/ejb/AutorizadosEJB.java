package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.CuentaFintech;
import es.uma.turingFintech.PersonaJuridica;
import es.uma.turingFintech.Usuario;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        //pj.setAutorizados(autorizadoList);
        List<PersonaJuridica> personaJuridicas = autorizado.getEmpresas();
        personaJuridicas.add(pj);
        //autorizado.setEmpresas(personaJuridicas);
        em.merge(autorizado);
        em.merge(pj);
    }

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


    public void eliminarAutorizados(Usuario u, Long ID)
            throws UsuarioNoEncontrado, NoEsAdministrativo, AutorizadoNoEncontradoException{

        gestionUsuarios.usuarioAdministrativo(u);

        Autorizado autorizadoExiste = em.find(Autorizado.class, ID);
        if(autorizadoExiste == null){
            throw new AutorizadoNoEncontradoException();
        }

            autorizadoExiste.setEstado("Baja");
            List<PersonaJuridica> vacia = null;
            autorizadoExiste.setEmpresas(vacia);
            em.merge(autorizadoExiste);

    }

    @Override
    public List<Autorizado> getAutorizados(){
        Query query = em.createQuery("select autorizado from Autorizado autorizado");
        List<Autorizado> autorizadoList = (List<Autorizado>) query.getResultList();
        return autorizadoList;
    }

}
