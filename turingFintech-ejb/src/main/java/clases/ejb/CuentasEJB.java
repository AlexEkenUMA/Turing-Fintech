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
import java.util.Date;

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
        //TODO
        // comprobar administrativo (Al principio), y comision
    }


    @Override
    public void cierreCuenta (String IBAN) throws CuentaNoEncontradaException, SaldoIncorrectoException {

        CuentaFintech cuentaEntity = em.find(CuentaFintech.class, IBAN);
        if (cuentaEntity == null){
            throw new CuentaNoEncontradaException();
        }
        if (cuentaEntity.getSaldo()!=0.0){
            throw new SaldoIncorrectoException();
        }
        Date fecha = new Date();
        cuentaEntity.setEstado(false);
        cuentaEntity.setFecha_cierre(fecha);
        em.merge(cuentaEntity);
    }
}
