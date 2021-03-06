package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;

import javax.ejb.Local;
import java.io.IOException;
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

    public void aperturaCuentaSegregada(Usuario u,Cliente cliente,String IBAN,String SWIFT, CuentaReferencia cr) throws UsuarioNoEncontrado, NoEsAdministrativo;

    public void aperturaCuentaPooled(Usuario u,Cliente cliente,String IBAN,String SWIFT, List<DepositadaEn> dpList) throws UsuarioNoEncontrado, NoEsAdministrativo;
    /**
     * Este método elimina una cuenta de la base de datos
     * @param IBAN
     * RF9
     */

    public void cierreCuenta (Usuario u,String IBAN)
            throws CuentaNoEncontradaException, SaldoIncorrectoException, UsuarioNoEncontrado, NoEsAdministrativo;

    public void setDepositos(String pooledAccount, String ibanCr, String abreviaturaDiv, Double saldo) throws CuentaNoEncontradaException, DivisaNoCoincide;

    public void darAltaRef(Usuario u, CuentaReferencia r) throws UsuarioNoEncontrado, NoEsAdministrativo;

    public PooledAccount getCuentaPooled (String iban) throws CuentaNoEncontradaException;

    public Cuenta getCuenta (String iban) throws CuentaNoEncontradaException;

    public List<CuentaFintech> getCuentasCliente (Long id);

    public List<Divisa> getDivisas();

    public List<CuentaReferencia> obtenerReferencias ();

    public List<CuentaFintech> obtenerCuentasFintech ();

    public List<Cuenta> getCuentas();

    public CuentaFintech obtenerCuentasFintechIban (String iban) throws CuentaNoEncontradaException;

    public Cuenta obtenerCuentaIban (String iban) throws CuentaNoEncontradaException;

    public Divisa obetenerDivisa(String abre) throws DivisaNoCoincide;

    public List<PooledAccount> obtenerCuentasPooled ();

    public List<Segregada> obtenerCuentasSegregada();

    public List<Segregada> getCuentasHolanda(String estado, String IBAN) throws NoEsAdministrativo, UsuarioNoEncontrado, NingunaCuentaCoincideConLosParametrosDeBusqueda;

    public String getInformeInicialAlemania(Usuario u) throws UsuarioNoEncontrado, NoEsAdministrativo, IOException;
    public String getInformeSemanalAlemania(Usuario u) throws UsuarioNoEncontrado, NoEsAdministrativo, IOException;

}
