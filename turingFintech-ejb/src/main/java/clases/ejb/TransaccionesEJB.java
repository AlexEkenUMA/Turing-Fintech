package clases.ejb;

import clases.ejb.exceptions.CuentaNoEncontradaException;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TransaccionesEJB implements GestionTransacciones {
    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;

    @EJB GestionUsuarios gestionUsuarios;

    @Override
    public void registrarTransaccionFintech(Usuario usuario, CuentaFintech origen, CuentaFintech destino, Transaccion transaccion)
            throws UsuarioNoEncontrado, NoEsAdministrativo, CuentaNoEncontradaException{

        gestionUsuarios.usuarioAdministrativo(usuario);

        CuentaFintech origenEntity  = em.find(CuentaFintech.class, origen.getIBAN());
        CuentaFintech destinoEntity = em.find(CuentaFintech.class, destino.getIBAN());
        if (origenEntity == null || destinoEntity == null){
            throw new CuentaNoEncontradaException();
        }
        transaccion.setOrigen(origen);
        transaccion.setDestino(destino);

        /**
         String tipo = destino.getTipo();
         if (tipo.equals("Pooled")){
         PooledAccount pooledAccount = em.find(PooledAccount.class, destino.getIBAN());
         if (pooledAccount == null){
         throw new CuentaNoEncontradaException();
         }
         List<DepositadaEn> depositadaEnList =  pooledAccount.getListaDepositos();
         Divisa divisa = transaccion.getEmisor();
         for (DepositadaEn dp : depositadaEnList){
         if (divisa == dp.getCuentaReferencia().getDivisa()){
         dp.setSaldo(dp.getSaldo()+transaccion.getCantidad());
         dp.getCuentaReferencia().setSaldo(dp.getCuentaReferencia().getSaldo()+transaccion.getCantidad());
         em.merge(dp);
         em.merge(dp.getCuentaReferencia());
         }
         }

         }
         */
        em.persist(transaccion);

    }

    @Override
    public void registratTransaccionCuenta(Usuario usuario, CuentaFintech origen, Cuenta destino, Transaccion transaccion)
            throws UsuarioNoEncontrado, NoEsAdministrativo, CuentaNoEncontradaException{

        gestionUsuarios.usuarioAdministrativo(usuario);

        CuentaFintech origenEntity  = em.find(CuentaFintech.class, origen.getIBAN());
        Cuenta        destinoEntity = em.find(Cuenta.class, destino.getIBAN());
        if (origenEntity == null || destinoEntity == null){
            throw new CuentaNoEncontradaException();
        }
        transaccion.setOrigen(origen);
        transaccion.setDestino(destino);
        em.persist(transaccion);


    }
}
