package clases.ejb;

import clases.ejb.exceptions.CuentaNoEncontradaException;
import clases.ejb.exceptions.SaldoIncorrectoException;
import clases.ejb.exceptions.TipoNoValidoException;
import es.uma.turingFintech.Cuenta;
import es.uma.turingFintech.CuentaFintech;
import es.uma.turingFintech.PooledAccount;
import es.uma.turingFintech.Segregada;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class CuentasEJB implements GestionCuentas {

    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;


    @Override
    public void aperturaCuenta(String IBAN,String SWIFT, String tipo) throws TipoNoValidoException{
        if (!tipo.equals("Pooled") && !tipo.equals("Segregada")){
            throw new TipoNoValidoException();
        }
        Date fecha = new Date();
        if (tipo.equals("Pooled")) {
            PooledAccount pooledAccount = new PooledAccount(IBAN, SWIFT, fecha, true, tipo, 0.00);
            em.persist(pooledAccount);
        }
        if (tipo.equals("Segregada")){
            Segregada segregada = new Segregada(IBAN, SWIFT, fecha, true, tipo, 0.00, 0.00);
            em.persist(segregada);
        }


        // TODO: En ambos casos las cuentas externas asociadas se añaden como información. Si hay varias divisas es necesario varias cuentas externas
        // comprobar administrativo (Al principio), y comision
    }


    @Override
    public void cierreCuenta (String IBAN) throws CuentaNoEncontradaException, SaldoIncorrectoException {
        // TODO: Comprobar si es administrativo
        CuentaFintech cuentaEntity = em.find(CuentaFintech.class, IBAN);
        if (cuentaEntity == null){
            throw new CuentaNoEncontradaException();
        }
        if (cuentaEntity.getSaldo()!=0.0){
            // TODO: Comprobar saldo en todas las divisas
            throw new SaldoIncorrectoException();
        }
        Date fecha = new Date();
        cuentaEntity.setEstado(false);
        cuentaEntity.setFecha_cierre(fecha);
        em.merge(cuentaEntity);
    }

    @Override
    public List<PooledAccount> obtenerCuentasPooled (){

        List<PooledAccount> pooled;
        Query query = em.createQuery("select cuenta from PooledAccount cuenta");
        pooled = (List<PooledAccount>) query.getResultList();
        return pooled;
    }

    @Override
    public List<Segregada> obtenerCuentasSegregada(){
        List<Segregada> segregadas = new ArrayList<>();


        return segregadas;
    }

}



