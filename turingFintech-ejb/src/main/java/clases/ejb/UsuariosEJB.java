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

    @PersistenceContext(unitName = "turingFintech-ejb")
    private EntityManager em;

    @Override
    public boolean usuarioCorrecto (Usuario u) throws AutorizadoBloqueado, AutorizadoSoloTieneAccesoACuentasClienteBloqueado, PersonaFisicaBloqueada, UsuarioNoEncontrado, EmpresaNoTieneAcceso, AccesoIncorrecto {
        boolean ok = false;
        Query query = em.createQuery("select usuario from Usuario usuario where usuario.nombre_usuario = :nombre " +
                "and usuario.contraseña = :password");
        query.setParameter("nombre", u.getNombre_usuario());
        query.setParameter("password", u.getContraseña());
        List<Usuario> usuarios = query.getResultList();


        if (usuarios.isEmpty()){
            throw new UsuarioNoEncontrado();
        }
        else if (usuarios.get(0).isAdministrativo()){
            throw new AccesoIncorrecto();
        }
        else{
            //el usuario que nos llega puede ser o cliente o autorizado, o las dos cosas
            //1. Caso solo cliente
            if(usuarios.get(0).getCliente() != null && usuarios.get(0).getAutorizado() == null){
                //si es una persona fisica, comprobamos si está bloqueado. En ese caso, no podra acceder a la aplicación
                if(usuarios.get(0).getCliente().getTipo_Cliente().equals("Fisica")){
                    if(usuarios.get(0).getCliente().getEstado().equals("Bloqueado")){
                        ok = false;
                        throw new PersonaFisicaBloqueada();
                    }
                    else{
                        ok = false;
                    }
                }
                //Empresas no tienen acceso a la aplicacion
                if(usuarios.get(0).getCliente().getTipo_Cliente().equals("Juridico")){
                    throw new EmpresaNoTieneAcceso();
                }
            }
            //2. Caso solo Autorizado
            else if(usuarios.get(0).getCliente() == null && usuarios.get(0).getAutorizado() != null){
                //autorizados bloqueados no tienen acceso a la aplicacion
                if(usuarios.get(0).getAutorizado().getEstado().equals("Bloqueado")){
                    ok = false;
                    throw new AutorizadoBloqueado();
                }
                else{
                    ok = true;
                }
            }
            //3. Las dos cosas
            else{
                //comprobamos su acceso a la aplicacion como cliente
                boolean accesoCorrectoCliente = false;
                if(usuarios.get(0).getCliente().getTipo_Cliente().equals("Fisica")){
                    if(usuarios.get(0).getCliente().getEstado().equals("Bloqueado")){
                        accesoCorrectoCliente = false;
                    }
                    else{
                        accesoCorrectoCliente = true;
                    }
                }
                //Empresas no tienen acceso a la aplicacion
                if(usuarios.get(0).getCliente().getTipo_Cliente().equals("Juridico")){
                    accesoCorrectoCliente = false;
                }
                //comprobamos su acceso a la aplicacion como autorizado
                boolean accesoCorrectoAutorizado = false;
                if(usuarios.get(0).getAutorizado().getEstado().equals("Bloqueado")){
                    accesoCorrectoAutorizado = false;
                }
                else{
                    accesoCorrectoAutorizado = true;
                }
                //tanto el cliente como el autorizado asociado al usuario que intenta loguearse estan bloqueados
                if(!accesoCorrectoCliente && !accesoCorrectoAutorizado){
                    ok = false;
                }
                //el autorizado asociado al usuario esta bloqueado, solo puede entrar como cliente
                if(accesoCorrectoCliente && !accesoCorrectoAutorizado){
                    ok = true;
                }
                //el cliente asociado esta bloqueado, solo puede entrar como autorizado
                if(!accesoCorrectoCliente && accesoCorrectoAutorizado){
                    List<PersonaJuridica> listapj = usuarios.get(0).getAutorizado().getEmpresas();
                    boolean solotieneaccesoacuentasdeclientebloqueado = true;
                    for(PersonaJuridica pj : listapj){
                        if(!pj.getEstado().equals("Bloqueado") && !pj.getCuentasFintech().isEmpty()){
                            solotieneaccesoacuentasdeclientebloqueado = false;
                        }
                    }
                    if(!solotieneaccesoacuentasdeclientebloqueado){
                        ok = true;
                    }
                    else{
                        ok = false;
                        throw new AutorizadoSoloTieneAccesoACuentasClienteBloqueado();
                    }
                }
                if(accesoCorrectoCliente && accesoCorrectoAutorizado){
                    List<PersonaJuridica> listapj = usuarios.get(0).getAutorizado().getEmpresas();
                    boolean solotieneaccesoacuentasdeclientebloqueado = true;
                    for(PersonaJuridica pj : listapj){
                        if(!pj.getEstado().equals("Bloqueado") && !pj.getCuentasFintech().isEmpty()){
                            solotieneaccesoacuentasdeclientebloqueado = false;
                        }
                    }
                    if(!solotieneaccesoacuentasdeclientebloqueado || !usuarios.get(0).getCliente().getCuentasFintech().isEmpty()){
                        ok = true;
                    }
                    else{
                        throw new AutorizadoSoloTieneAccesoACuentasClienteBloqueado();
                    }
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




    @Override
    public Usuario refrescarUsuario(Usuario u) throws AutorizadoBloqueado, UsuarioNoEncontrado, EmpresaNoTieneAcceso, AutorizadoSoloTieneAccesoACuentasClienteBloqueado, PersonaFisicaBloqueada, AccesoIncorrecto {
        usuarioCorrecto(u);
        Usuario user = em.find(Usuario.class, u.getNombre_usuario());
        em.refresh(user);
        return user;

    }

    @Override
    public Usuario refrescarUsuarioAdmin(Usuario u) throws AutorizadoBloqueado, UsuarioNoEncontrado, EmpresaNoTieneAcceso, AutorizadoSoloTieneAccesoACuentasClienteBloqueado, PersonaFisicaBloqueada, NoEsAdministrativo {
        usuarioAdministrativo(u);
        Usuario user = em.find(Usuario.class, u.getNombre_usuario());
        em.refresh(user);
        return user;

    }

    @Override
    public void nuevoUsuario(Usuario u,String nombre, String contrasena, Boolean admin, Autorizado au) throws UsuarioNombreRepetido, UsuarioNoEncontrado, NoEsAdministrativo {
        usuarioAdministrativo(u);
        Usuario prueba = em.find(Usuario.class, nombre);
        if (prueba != null){
            throw new UsuarioNombreRepetido();
        }
        Usuario usuario = new Usuario(nombre, contrasena, admin);
        if (au != null){
            usuario.setAutorizado(au);
        }
       em.persist(usuario);
    }

}
