import es.uma.turingFintech.Cuenta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CuentasEJB implements GestionCuentas {

    @PersistenceContext(name = "turingFintech")
    private EntityManager em;


    @Override
    public void aperturaCuenta(String IBAN, String tipo){

        //TODO

    }


    @Override
    public void cierreCuenta (Cuenta cuenta){

        //TODO

    }
}
