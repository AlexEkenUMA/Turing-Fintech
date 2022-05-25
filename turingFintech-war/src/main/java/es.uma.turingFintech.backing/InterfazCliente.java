package es.uma.turingFintech.backing;


import clases.ejb.GestionCuentas;
import clases.ejb.GestionTransacciones;
import es.uma.turingFintech.CuentaFintech;
import es.uma.turingFintech.Transaccion;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named(value = "interfazCliente")
@RequestScoped
public class InterfazCliente {


    @Inject
    private GestionCuentas gestionCuentas;

    @Inject
    private GestionTransacciones gestionTransacciones;

    @Inject
    private InfoSesion sesion;

    private CuentaFintech cuenta;

    private String iban="";

    public InterfazCliente(){
        cuenta = new CuentaFintech();
    }

    public String getIban() {return iban;}

    public void setIban(String iban) {this.iban = iban;}

    public String verMovimientos (CuentaFintech c){
        cuenta = c;
        iban = c.getIBAN();
        return "transacciones.xhtml";
    }

    public List<Transaccion> getTransaccionesEmitidad(){return gestionTransacciones.getTransaccionesEmitidad(iban);}


    public List<Transaccion> getTransaccionesRecibidad(){return gestionTransacciones.getTransaccionesRecibidad(iban);}

}
