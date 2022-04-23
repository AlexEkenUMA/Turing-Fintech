package clases.ejb;

import es.uma.turingFintech.Cuenta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CuentasEJB implements GestionCuentas {

    @PersistenceContext(name = "turingFintech-ejb")
    private EntityManager em;


    @Override
    public void aperturaCuenta(String IBAN, String tipo){
        if (!tipo.equals("Pooled")){

        }
        if (!tipo.equals("Segregada")){

        }
        //TODO

    }


    @Override
    public void cierreCuenta (Cuenta cuenta){

        //TODO

    }
}
