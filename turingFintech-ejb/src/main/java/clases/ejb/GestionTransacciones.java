package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.Cuenta;
import es.uma.turingFintech.CuentaFintech;
import es.uma.turingFintech.Transaccion;
import es.uma.turingFintech.Usuario;

import javax.ejb.Local;
import java.util.List;

@Local
public interface GestionTransacciones {

    public void registrarTransaccionFintech(Usuario usuario, CuentaFintech origen, Cuenta destino, Transaccion transaccion)
            throws MismaCuentaOrigenYDestino, CuentaDeBajaNoPuedeRegistrarTransaccion, TransaccionConCantidadIncorrecta, SaldoInsuficiente, DivisaNoCoincide, UsuarioNoEncontrado, NoEsAdministrativo, CuentaNoEncontradaException;

    public List<Transaccion> getTransaccionesEmitidad(String iban);

    public List<Transaccion> getTransaccionesRecibidad(String iban);

}
