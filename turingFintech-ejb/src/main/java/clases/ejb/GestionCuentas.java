package clases.ejb;

import clases.ejb.exceptions.TipoNoValidoException;
import es.uma.turingFintech.Cuenta;

import javax.ejb.Local;

@Local
public interface GestionCuentas {


    /**
     * Este método debe abrir una cuenta en la base datos
     *
     * @param IBAN
     * @param tipo
     * RF5
     */
    public void aperturaCuenta(String IBAN,String SWIFT, String tipo) throws TipoNoValidoException;

    /**
     * Este método elimina una cuenta de la base de datos
     * @param cuenta
     * RF9
     */

    public void cierreCuenta (Cuenta cuenta);
}
