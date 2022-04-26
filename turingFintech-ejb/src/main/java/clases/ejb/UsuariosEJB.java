package clases.ejb;

import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.Usuario;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UsuariosEJB implements GestionUsuarios {

    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;

    @Override
    public boolean usuarioCorrecto (Usuario u) throws UsuarioNoEncontrado{
        boolean ok = false;
        Query query = em.createQuery("select usuario from Usuario usuario where usuario.nombre_usuario = :nombre " +
                "and usuario.contraseña = :password");
        query.setParameter("nombre", u.getNombre_usuario());
        query.setParameter("password", u.getContraseña());
        List<Usuario> usuarios = query.getResultList();
        if (usuarios.isEmpty()){
            throw new UsuarioNoEncontrado();
        }else{
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean usuarioAdministrativo (Usuario u) throws UsuarioNoEncontrado, NoEsAdministrativo{
        boolean ok = false;
        Query query = em.createQuery("select usuario from Usuario usuario where usuario.nombre_usuario = :nombre " +
                "and usuario.contraseña = :password");
        query.setParameter("nombre", u.getNombre_usuario());
        query.setParameter("password", u.getContraseña());
        List<Usuario> usuarios = query.getResultList();
        if (usuarios.isEmpty()){
            throw new UsuarioNoEncontrado();
        }
        if (!usuarios.get(0).isAdministrativo()){
            throw new NoEsAdministrativo();
        }
        ok = true;
        return ok;
    }


}