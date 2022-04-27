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
public class CuentasEJB implements GestionCuentas {

    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;

    @EJB GestionUsuarios gestionUsuarios;

    @Override
    public void aperturaCuenta(Usuario u,Cliente cliente,String IBAN,String SWIFT, String tipo, List<DepositadaEn> dpList)
            throws TipoNoValidoException, UsuarioNoEncontrado, NoEsAdministrativo {

        gestionUsuarios.usuarioAdministrativo(u);

        if (!tipo.equals("Pooled") && !tipo.equals("Segregada")){
            throw new TipoNoValidoException();
        }
        Date fecha = new Date();
        if (tipo.equals("Pooled")) {
            PooledAccount pooledAccount = new PooledAccount(IBAN, SWIFT, fecha, true, tipo);
           // em.persist(cliente);

            for (DepositadaEn dp : dpList){
                if (dp != null) {
                    dp.setPooledAccount(pooledAccount);
                    em.persist(dp);
                    em.persist(dp.getCuentaReferencia());
                }

            }
            pooledAccount.setCliente(cliente);
            pooledAccount.setListaDepositos(dpList);
            em.persist(pooledAccount);
        }
        if (tipo.equals("Segregada")){
            Segregada segregada = new Segregada(IBAN, SWIFT, fecha, true, tipo,0.00);
            segregada.setCliente(cliente);
            segregada.setCr(dpList.get(0).getCuentaReferencia());
            em.persist(segregada);
        }
    }


    @Override
    public void cierreCuenta (Usuario u, String IBAN)
            throws CuentaNoEncontradaException, SaldoIncorrectoException, UsuarioNoEncontrado, NoEsAdministrativo
    {
        gestionUsuarios.usuarioAdministrativo(u);

        Date fecha = new Date();
        CuentaFintech cuentaEntity = em.find(CuentaFintech.class, IBAN);
        if (cuentaEntity == null){
            throw new CuentaNoEncontradaException();
        }
        if (cuentaEntity.getTipo().equals("Pooled")){
            PooledAccount pooledEntity = em.find(PooledAccount.class, IBAN);
            if (pooledEntity == null){
                throw new CuentaNoEncontradaException();
            }
            List<DepositadaEn> dpList = pooledEntity.getListaDepositos();
            boolean ok = true;
            for (DepositadaEn dp : dpList){
                if (dp.getSaldo() != 0){
                    ok = false;
                }
            }
        if (!ok){
            throw new SaldoIncorrectoException();
        }
        pooledEntity.setEstado(false);
        pooledEntity.setFecha_cierre(fecha);
        em.merge(pooledEntity);
        }
        if (cuentaEntity.getTipo().equals("Segregada")){
            Segregada segregadaEntity = em.find(Segregada.class, IBAN);
            if (segregadaEntity == null){
                throw new CuentaNoEncontradaException();
            }
            if (segregadaEntity.getCr().getSaldo() != 0){
                throw new SaldoIncorrectoException();
            }
            segregadaEntity.setFecha_cierre(fecha);
            segregadaEntity.setEstado(false);
            em.merge(segregadaEntity);
        }
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
        List<Segregada> segregadas;
        Query query = em.createQuery("select cuenta from Segregada cuenta");
        segregadas = (List<Segregada>) query.getResultList();
        return segregadas;
    }

}



