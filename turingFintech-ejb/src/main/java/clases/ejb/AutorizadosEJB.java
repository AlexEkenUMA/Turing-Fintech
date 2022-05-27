package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
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
            autporizadoEntity = autorizado;
        }

        List<Autorizado> autorizadoList = pj.getAutorizados();
        autorizadoList.add(autorizado);
       // pj.setAutorizados(autorizadoList);
        List<PersonaJuridica> personaJuridicas = autporizadoEntity.getEmpresas();
        personaJuridicas.add(pj);
       // autorizado.setEmpresas(personaJuridicas);
        //em.merge(autorizado);
        //em.merge(pj);
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
            au.setUsuario(autorizadoExiste.getUsuario());
            au.setEmpresas(autorizadoExiste.getEmpresas());

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

    @Override
    public void nuevoAutorizado (Usuario u, Autorizado autorizado, Long id, Usuario usuario) throws UsuarioNoEncontrado, NoEsAdministrativo, PersonaJuridicaNoEncontrada, UsuarioNombreRepetido {
        gestionUsuarios.usuarioAdministrativo(u);
        PersonaJuridica personaJuridica = em.find(PersonaJuridica.class, id);
        if (personaJuridica == null){
            throw new PersonaJuridicaNoEncontrada();
        }
        Usuario usuario1 = em.find(Usuario.class, usuario.getNombre_usuario());
        if (usuario1 != null){
            throw new UsuarioNombreRepetido();
        }
        List<PersonaJuridica> personaJuridicas = new ArrayList<>();
        personaJuridicas.add(personaJuridica);
        autorizado.setEmpresas(personaJuridicas);
        autorizado.setEstado("Activo");
        autorizado.setFecha_Inicio(new Date());
        autorizado.setUsuario(usuario);
        em.persist(autorizado);
        Autorizado au = em.find(Autorizado.class, autorizado.getId());
        Cliente cliente = em.find(Cliente.class, id);
        usuario.setAutorizado(autorizado);
        usuario.setCliente(cliente);
        em.persist(usuario);

    }

}
