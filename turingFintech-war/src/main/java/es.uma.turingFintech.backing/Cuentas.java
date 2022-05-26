package es.uma.turingFintech.backing;

import clases.ejb.GestionClientes;
import clases.ejb.GestionCuentas;
import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named(value = "cuentas")
@RequestScoped
public class Cuentas {

    public static enum Modo {
        SEGREGADA,
        POOLED,
        NOACCION,
        DEPOSITO
    };

    String pooled = "Pooled";

    String dni = "";

    String ibanCr = "";

    String ibanPooled = "";

    String divisa = "";

    Double saldo =0.0;

    private CuentaReferencia cuentaReferencia;

    private Segregada segregada;

    private PooledAccount pooledAccount;

    Usuario usuario;

    @Inject
    InfoSesion sesion;

    @Inject
    GestionCuentas gestionCuentas;

    @Inject
    GestionClientes gestionClientes;

    private Modo modo;


    public Cuentas(){
        segregada = new Segregada();
        pooledAccount = new PooledAccount();
        cuentaReferencia = new CuentaReferencia();
        modo = Modo.NOACCION;
    }

    public CuentaReferencia getCuentaReferencia() {return cuentaReferencia;}

    public void setCuentaReferencia(CuentaReferencia cuentaReferencia) {this.cuentaReferencia = cuentaReferencia;}

    public Double getSaldo() {return saldo;}

    public void setSaldo(Double saldo) {this.saldo = saldo;}

    public String getIbanPooled() {return ibanPooled;}

    public void setIbanPooled(String ibanPooled) {this.ibanPooled = ibanPooled;}

    public String getPooled() {
        return pooled;
    }

    public void setPooled(String pooled) {
        this.pooled = pooled;
    }

    public Segregada getSegregada() {return segregada;}

    public void setSegregada(Segregada segregada) {this.segregada = segregada;}

    public PooledAccount getPooledAccount() {return pooledAccount;}

    public void setPooledAccount(PooledAccount pooledAccount) {this.pooledAccount = pooledAccount;}

    public String getDni() {return dni;}

    public void setDni(String dni) {this.dni = dni;}

    public String getIbanCr() {return ibanCr;}

    public void setIbanCr(String ibanCr) {this.ibanCr = ibanCr;}

    public Modo getModo() {return modo;}

    public void setModo(Modo modo) {this.modo = modo;}

    public String getDivisa() {return divisa;}

    public void setDivisa(String divisa) {this.divisa = divisa;}

    public String aperturaSegregada(){
        modo = Modo.SEGREGADA;
        return "aperturaSegregada"; //Añadir panel segregada
    }

    public String aperturaPooled(){
        modo = Modo.POOLED;
        return "aperturaPooled"; //Añadir panel pooled
    }

    public void asociarCliente (String iden){
        List<Cliente> clienteList = gestionClientes.getClientes();
        Cliente objetivo = new Cliente();
        for (Cliente c : clienteList){
            if (c.getIdentificacion().equals(iden)){
                objetivo = c;
            }
        }
        segregada.setCliente(objetivo);
        pooledAccount.setCliente(objetivo);
    }

    public void asociarReferencia (String iban){
        List<CuentaReferencia> cuentaReferenciaList = gestionCuentas.obtenerReferencias();
        CuentaReferencia objetivo = new CuentaReferencia();
        for (CuentaReferencia cr : cuentaReferenciaList){
            if (iban.equals(cr.getIBAN())){
                objetivo = cr;
            }
        }
        segregada.setCr(objetivo);
    }

    public String aperturaReferencia(){
        return null;
    }


    public String aniadirDeposito(String pooledAccount){
        ibanPooled = pooledAccount;
        modo = Modo.DEPOSITO;
        return "aniadirDeposito.xhtml";
    }




    public String accion(){

        //public void aperturaCuentaSegregada(Usuario u,Cliente cliente,String IBAN,String SWIFT, CuentaReferencia cr)
        try{
            if (modo.equals(Modo.SEGREGADA)){
                asociarCliente(dni);
                //asociarReferencia(ibanCr);
                cuentaReferencia.setEstado(true);
                cuentaReferencia.setFecha_Apertura(new Date());
                gestionCuentas.aperturaCuentaSegregada(sesion.getUsuario(), segregada.getCliente(), segregada.getIBAN(),
                        segregada.getSWIFT(), cuentaReferencia);
            }

            if (modo.equals((Modo.POOLED))){

                asociarCliente(dni);
               List<DepositadaEn> depositadaEns = new ArrayList<>();
                Cuenta prueba = gestionCuentas.getCuenta(ibanCr);
                if (prueba == null){
                    throw new CuentaNoEncontradaException();
                }

                gestionCuentas.aperturaCuentaPooled(sesion.getUsuario(), pooledAccount.getCliente(), pooledAccount.getIBAN(), pooledAccount.getSWIFT()
                , depositadaEns);

                gestionCuentas.setDepositos(pooledAccount.getIBAN(), ibanCr, divisa, saldo);
            }

            if (modo.equals(Modo.DEPOSITO)){
                pooledAccount = gestionCuentas.getCuentaPooled(ibanPooled);
                gestionCuentas.setDepositos(pooledAccount.getIBAN(), ibanCr, divisa, saldo);
            }

            return "panelAdmin.xhtml";


        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        } catch (CuentaNoEncontradaException e) {
            e.printStackTrace();
        } catch (DivisaNoCoincide divisaNoCoincide) {
            divisaNoCoincide.printStackTrace();
        }
        return null;
    }

    public String darBajar (String iban){
        try{
            gestionCuentas.cierreCuenta(sesion.getUsuario(), iban);
            return "panelAdmin.xhtml";
        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (CuentaNoEncontradaException e) {
            e.printStackTrace();
        } catch (SaldoIncorrectoException e) {
            e.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        }

        return null;
    }


}
