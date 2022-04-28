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

@Stateless
public class AutorizadosEJB implements GestionAutorizados{
    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;

    @EJB GestionUsuarios gestionUsuarios;

    @Override
    public void anadirAutorizados (Usuario u, PersonaJuridica pj) throws NoEsAdministrativo,
            UsuarioNoEncontrado, PersonaJuridicaNoEncontrada{

        gestionUsuarios.usuarioAdministrativo(u);

        PersonaJuridica personaJuridica = em.find(PersonaJuridica.class, pj.getId());
        if (personaJuridica == null){
            throw new PersonaJuridicaNoEncontrada();
        }
        Autorizado autorizado = new Autorizado();




    }

}
