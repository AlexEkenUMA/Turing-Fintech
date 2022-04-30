package clases.ejb;

import clases.ejb.exceptions.*;
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
    public void registrarTransaccionFintech(Usuario usuario, CuentaFintech origen, Cuenta destino, Transaccion transaccion)
            throws MismaCuentaOrigenYDestino, CuentaDeBajaNoPuedeRegistrarTransaccion, TransaccionConCantidadIncorrecta, SaldoInsuficiente, DivisaNoCoincide, UsuarioNoEncontrado, NoEsAdministrativo, CuentaNoEncontradaException{
        gestionUsuarios.usuarioAdministrativo(usuario);
        if(transaccion.getCantidad() <= 0){
            throw new TransaccionConCantidadIncorrecta();
        }
        if(origen.getEstado().equals("Baja")){
            throw new CuentaDeBajaNoPuedeRegistrarTransaccion();
        }
        boolean okOrigen = false;
        boolean okDestino = false;
        CuentaFintech origenEntity  = em.find(CuentaFintech.class, origen.getIBAN());
        Cuenta destinoEntity = em.find(Cuenta.class, destino.getIBAN());
        if (origenEntity == null || destinoEntity == null){
            throw new CuentaNoEncontradaException();
        }
        if(origenEntity.getIBAN().equals(destinoEntity.getIBAN())){
            throw new MismaCuentaOrigenYDestino();
        }
        if(origenEntity instanceof PooledAccount){
            PooledAccount pooledOrigen = (PooledAccount) origenEntity;
            transaccion.setOrigen(pooledOrigen);
            for(DepositadaEn dp : pooledOrigen.getListaDepositos()){
                if(dp.getCuentaReferencia().getDivisa().equals(transaccion.getEmisor())){
                    okOrigen = true;
                    double nuevoSaldo = dp.getSaldo()-transaccion.getCantidad()-transaccion.getComision();
                    double nuevoSaldoCr = dp.getCuentaReferencia().getSaldo()- transaccion.getCantidad()- transaccion.getComision();
                    if(nuevoSaldo < 0 || nuevoSaldoCr < 0){
                        throw new SaldoInsuficiente();
                    }
                    else{
                        dp.setSaldo(nuevoSaldo);
                        //no se si es necesario
                        dp.getCuentaReferencia().setSaldo(nuevoSaldoCr);
                        em.merge(dp);
                        em.merge(dp.getCuentaReferencia());
                    }
                }
            }
            if(!okOrigen){
                throw new DivisaNoCoincide();
            }
        }
        if(origenEntity instanceof Segregada){
            Segregada segregadaOrigen = (Segregada) origenEntity;
            transaccion.setOrigen(segregadaOrigen);
            //Buscar solo la cuenta de referencia (no las depositadas) ******
            for(DepositadaEn dp : segregadaOrigen.getCr().getListaDepositos()){
                if(dp.getCuentaReferencia().getDivisa().equals(transaccion.getEmisor())){
                    okOrigen = true;
                    double nuevoSaldo = dp.getSaldo()-transaccion.getCantidad()-transaccion.getComision();
                    double nuevoSaldoCr = dp.getCuentaReferencia().getSaldo()- transaccion.getCantidad()- transaccion.getComision();
                    if(nuevoSaldo < 0 || nuevoSaldoCr < 0){
                        throw new SaldoInsuficiente();
                    }
                    else{
                        dp.setSaldo(nuevoSaldo);
                        //no se si es necesario
                        dp.getCuentaReferencia().setSaldo(nuevoSaldoCr);
                        em.merge(dp);
                        em.merge(dp.getCuentaReferencia());
                    }
                }
            }
            if(!okOrigen){
                throw new DivisaNoCoincide();
            }
        }
        if(destinoEntity instanceof PooledAccount){
            PooledAccount pooledDestino = (PooledAccount) destinoEntity;
            if(pooledDestino.getEstado().equals("Baja")){
                throw new CuentaDeBajaNoPuedeRegistrarTransaccion();
            }
            transaccion.setDestino(pooledDestino);
            for(DepositadaEn dp : pooledDestino.getListaDepositos()){
                if(dp.getCuentaReferencia().getDivisa().equals(transaccion.getEmisor())){
                    okDestino = true;
                    dp.setSaldo(dp.getSaldo()+transaccion.getCantidad());
                    //no se si es necesario
                    dp.getCuentaReferencia().setSaldo(dp.getCuentaReferencia().getSaldo()+transaccion.getCantidad());
                    em.merge(dp);
                    em.merge(dp.getCuentaReferencia());
                }
            }
            if(!okDestino){
                throw new DivisaNoCoincide();
            }
        }
        if(destinoEntity instanceof  Segregada){
            Segregada segregadaDestino = (Segregada) destinoEntity;
            if(segregadaDestino.getEstado().equals("Baja")){
                throw new CuentaDeBajaNoPuedeRegistrarTransaccion();
            }
            transaccion.setDestino(segregadaDestino);
            // No hace falta miras en las depositadas ****
            for(DepositadaEn dp : segregadaDestino.getCr().getListaDepositos()){
                if(dp.getCuentaReferencia().getDivisa().equals(transaccion.getEmisor())){
                    okDestino = true;
                    dp.setSaldo(dp.getSaldo()+transaccion.getCantidad());
                    //no se si es necesario
                    dp.getCuentaReferencia().setSaldo(dp.getCuentaReferencia().getSaldo()+transaccion.getCantidad());
                    em.merge(dp);
                    em.merge(dp.getCuentaReferencia());
                }
            }
            if(!okDestino){
                throw new DivisaNoCoincide();
            }
        }
        if(destinoEntity instanceof CuentaReferencia){
            CuentaReferencia crDestino = (CuentaReferencia) destinoEntity;
            if(!crDestino.getEstado()){
                throw new CuentaDeBajaNoPuedeRegistrarTransaccion();
            }
            transaccion.setDestino(crDestino);
            for(DepositadaEn dp : crDestino.getListaDepositos()){
                if(dp.getCuentaReferencia().getDivisa().equals(transaccion.getEmisor())){
                    okDestino = true;
                    dp.setSaldo(dp.getSaldo()+transaccion.getCantidad());
                    //no se si es necesario
                    dp.getCuentaReferencia().setSaldo(dp.getCuentaReferencia().getSaldo()+transaccion.getCantidad());
                    em.merge(dp);
                    em.merge(dp.getCuentaReferencia());
                }
            }
            if(!okDestino){
                throw new DivisaNoCoincide();
            }
        }
        em.persist(transaccion);
    }

}
