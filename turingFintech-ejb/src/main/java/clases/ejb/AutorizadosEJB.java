package clases.ejb;

import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.PersonaJuridicaNoEncontrada;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.PersonaJuridica;
import es.uma.turingFintech.Usuario;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

}
