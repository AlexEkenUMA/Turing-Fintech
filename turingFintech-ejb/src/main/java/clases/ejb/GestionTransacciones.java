package clases.ejb;

import clases.ejb.exceptions.CuentaNoEncontradaException;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.Cuenta;
import es.uma.turingFintech.CuentaFintech;
import es.uma.turingFintech.Transaccion;
import es.uma.turingFintech.Usuario;

import javax.ejb.Local;

@Local
public interface GestionTransacciones {

    public void registrarTransaccionFintech(Usuario usuario, CuentaFintech origen, CuentaFintech destino, Transaccion transaccion)
            throws UsuarioNoEncontrado, NoEsAdministrativo, CuentaNoEncontradaException;


    public void registratTransaccionCuenta(Usuario usuario, CuentaFintech origen, Cuenta destino, Transaccion transaccion)
        throws UsuarioNoEncontrado, NoEsAdministrativo, CuentaNoEncontradaException;




}
