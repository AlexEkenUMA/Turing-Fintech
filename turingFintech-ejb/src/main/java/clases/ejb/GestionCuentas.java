package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface GestionCuentas {


    /**
     * Este método debe abrir una cuenta en la base datos
     *
     * @param IBAN
     * @param tipo
     * RF5
     */
    public void aperturaCuenta(Usuario u, Cliente cliente, String IBAN, String SWIFT, String tipo, List<DepositadaEn> dp)
            throws TipoNoValidoException, UsuarioNoEncontrado, NoEsAdministrativo;

    /**
     * Este método elimina una cuenta de la base de datos
     * @param IBAN
     * RF9
     */

    public void cierreCuenta (Usuario u,String IBAN)
            throws CuentaNoEncontradaException, SaldoIncorrectoException, UsuarioNoEncontrado, NoEsAdministrativo;

    public List<PooledAccount> obtenerCuentasPooled ();

    public List<Segregada> obtenerCuentasSegregada();

    public List<Segregada> getCuentasHolanda();

}
