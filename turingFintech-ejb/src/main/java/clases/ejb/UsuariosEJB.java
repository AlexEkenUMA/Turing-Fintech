package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.Autorizado;
import es.uma.turingFintech.PersonaJuridica;
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
    public boolean usuarioCorrecto (Usuario u) throws AutorizadoSoloTieneAccesoACuentasClienteBloqueado, PersonaFisicaBloqueada, UsuarioNoEncontrado, EmpresaNoTieneAcceso {
        boolean ok = false;
        Query query = em.createQuery("select usuario from Usuario usuario where usuario.nombre_usuario = :nombre " +
                "and usuario.contraseña = :password");
        query.setParameter("nombre", u.getNombre_usuario());
        query.setParameter("password", u.getContraseña());
        List<Usuario> usuarios = query.getResultList();

        if (usuarios.isEmpty()){
            throw new UsuarioNoEncontrado();
        }else{
            //si el usuario no tiene ningun cliente asociado, suponemos que podrá entrar a la aplicacion
            //si el usuario no pertenece a ningun autorizado, suponemos que podrá entrar a la aplicacion
            if(usuarios.get(0).getCliente() == null && usuarios.get(0).getAutorizado() == null){
                ok = true;
            }
            else{
                //si es un autorizado, comprobamos el n de cuentas a la que tiene acceso. Si solo tiene acceso a operar
                //con una cuenta y esta bloqueado, le denegamos el acceso a la aplicacion
                if(usuarios.get(0).getAutorizado() != null){
                    Autorizado au = usuarios.get(0).getAutorizado();
                    boolean solotieneaccesoacuentasdeclientebloqueado = true;
                    List<PersonaJuridica> listapj = au.getEmpresas();
                    for(PersonaJuridica pj : listapj){
                        if(!pj.getEstado().equals("Bloqueado") && !pj.getCuentasFintech().isEmpty()){
                            solotieneaccesoacuentasdeclientebloqueado = false;
                        }
                    }
                    if(!solotieneaccesoacuentasdeclientebloqueado){
                        ok = true;
                    }
                    else{
                        throw new AutorizadoSoloTieneAccesoACuentasClienteBloqueado();
                    }
                }
                //si es una persona fisica, comprobamos si está bloqueado. En ese caso, no podra acceder a la aplicación
                if(usuarios.get(0).getCliente().getTipo_Cliente().equals("Fisica")){
                    if(!usuarios.get(0).getCliente().getEstado().equals("Bloqueado")){
                        ok = true;
                    }
                    else{
                        throw new PersonaFisicaBloqueada();
                    }
                }
                //Empresas no tienen acceso a la aplicacion
                if(usuarios.get(0).getCliente().getTipo_Cliente().equals("Juridico")){
                    throw new EmpresaNoTieneAcceso();
                }
            }
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
