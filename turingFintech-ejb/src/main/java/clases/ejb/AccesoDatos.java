package clases.ejb;

import es.uma.turingFintech.Cliente;
import es.uma.turingFintech.CuentaFintech;
import es.uma.turingFintech.PooledAccount;
import es.uma.turingFintech.Segregada;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.Closeable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccesoDatos implements Closeable {
    private EntityManagerFactory emf;
    private EntityManager em;

    /**
     * Constructor por defecto. Crea un contexto de persistencia.
     */
    public AccesoDatos() {
        emf = Persistence.createEntityManagerFactory("turingFintech-ejb");
        em = emf.createEntityManager();
    }

    /**
     * Cierra el contexto de persistencia.
     */
    @Override
    public void close() {
        em.close();
        emf.close();
    }

    public List<CuentaFintech> getCuentas(){
        Query query = em.createQuery("SELECT cf FROM CuentaFintech cf where tipo='segregada' or tipo='dedicada'");
        List<CuentaFintech> lista = query.getResultList();
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, Calendar.YEAR - 3);
        Date limite = calendario.getTime();
        for(CuentaFintech cf : lista){
            if(cf.getFecha_cierre() != null){
                lista.remove(cf);
            }
            if(limite.after(cf.getFecha_Apertura())){
                lista.remove(cf);
            }
        }
        return lista;
    }

    public List<Cliente> getClientes(){
        Query query = em.createQuery("SELECT c FROM Cliente c");
        List<Cliente> lista = query.getResultList();
        return lista;
    }






}
