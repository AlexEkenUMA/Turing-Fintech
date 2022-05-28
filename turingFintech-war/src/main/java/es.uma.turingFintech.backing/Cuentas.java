package es.uma.turingFintech.backing;

import clases.ejb.GestionClientes;
import clases.ejb.GestionCuentas;
import clases.ejb.GestionTransacciones;
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
        DEPOSITO,
        POOLEDREF,
        TRANSACCION
    };

    String opcion = "";

    String pooled = "Pooled";

    String dni = "";

    String ibanCr = "";

    String ibanPooled = "";

    String divisa = "";

    Double saldo =0.0;

    private CuentaReferencia cuentaReferencia;

    private Segregada segregada;

    private PooledAccount pooledAccount;

    private Transaccion transaccion;

    private String origen = "";
    private String destino = "";

    private String divisaOrigen = "";
    private String divisaDestino = "";

    Usuario usuario;

    @Inject
    InfoSesion sesion;

    @Inject
    GestionCuentas gestionCuentas;

    @Inject
    GestionClientes gestionClientes;

    @Inject
    GestionTransacciones gestionTransacciones;

    private Modo modo;


    public Cuentas(){
        segregada = new Segregada();
        pooledAccount = new PooledAccount();
        cuentaReferencia = new CuentaReferencia();
        transaccion = new Transaccion();
        modo = Modo.NOACCION;
    }

    public String getDivisaOrigen() {return divisaOrigen;}

    public void setDivisaOrigen(String divisaOrigen) {this.divisaOrigen = divisaOrigen;}

    public String getDivisaDestino() {return divisaDestino;}

    public void setDivisaDestino(String divisaDestino) {this.divisaDestino = divisaDestino;}

    public Transaccion getTransaccion() {return transaccion;}

    public void setTransaccion(Transaccion transaccion) {this.transaccion = transaccion;}

    public String getOpcion() {return opcion;}

    public void setOpcion(String opcion) {this.opcion = opcion;}

    public CuentaReferencia getCuentaReferencia() {return cuentaReferencia;}

    public void setCuentaReferencia(CuentaReferencia cuentaReferencia) {this.cuentaReferencia = cuentaReferencia;}

    public Double getSaldo() {return saldo;}

    public void setSaldo(Double saldo) {this.saldo = saldo;}

    public String getIbanPooled() {return ibanPooled;}

    public void setIbanPooled(String ibanPooled) {this.ibanPooled = ibanPooled;}

    public String getPooled() {
        return pooled;
    }

    public String getOrigen() {return origen;}

    public void setOrigen(String origen) {this.origen = origen;}

    public String getDestino() {return destino;}

    public void setDestino(String destino) {this.destino = destino;}

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
        if (opcion.equals("no")){
            modo = Modo.POOLEDREF;
            return "pooledRef.xhtml";
        }
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

   public void asociarDivisa () throws DivisaNoCoincide {
        Divisa divisa = gestionCuentas.obetenerDivisa(this.divisa);
        cuentaReferencia.setDivisa(divisa);
   }


    public String aniadirDeposito(String pooledAccount){
        ibanPooled = pooledAccount;
        modo = Modo.DEPOSITO;
        return "aniadirDeposito.xhtml";
    }


    public String registroOrigen(String o){
        origen = o;
        return "transaccion.xhtml";
    }

    public String regsitroDestino(String d){
        modo = Modo.TRANSACCION;
        destino = d;
        return "registroTransaccion.xhtml";
    }



    public String accion(){

        //public void aperturaCuentaSegregada(Usuario u,Cliente cliente,String IBAN,String SWIFT, CuentaReferencia cr)
        try{
            if (modo.equals(Modo.SEGREGADA)){
                asociarCliente(dni);
                //asociarReferencia(ibanCr);
                cuentaReferencia.setEstado(true);
                cuentaReferencia.setFecha_Apertura(new Date());
                asociarDivisa();
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

            if (modo.equals(Modo.POOLEDREF)){
                asociarCliente(dni);
                List<DepositadaEn> depositadaEns = new ArrayList<>();
                gestionCuentas.aperturaCuentaPooled(sesion.getUsuario(), pooledAccount.getCliente(), pooledAccount.getIBAN(), pooledAccount.getSWIFT()
                ,depositadaEns);
                cuentaReferencia.setEstado(true);
                cuentaReferencia.setFecha_Apertura(new Date());
                asociarDivisa();
                gestionCuentas.darAltaRef(sesion.getUsuario(), cuentaReferencia);
                gestionCuentas.setDepositos(pooledAccount.getIBAN(), cuentaReferencia.getIBAN(),divisa,  saldo);
            }

            if (modo.equals(Modo.TRANSACCION)){
                CuentaFintech cuentaOrigen = gestionCuentas.obtenerCuentasFintechIban(origen);
                Cuenta cuentaDestino = gestionCuentas.getCuenta(destino);
                Divisa divO = gestionCuentas.obetenerDivisa(divisaOrigen);
                Divisa divD = gestionCuentas.obetenerDivisa(divisaDestino);
                transaccion.setEmisor(divO);
                transaccion.setReceptor(divD);
                transaccion.setFecha_Instruccion(new Date());
                gestionTransacciones.registrarTransaccionFintech(sesion.getUsuario(), cuentaOrigen, cuentaDestino, transaccion);

            }

            return "cuentas.xhtml";


        } catch (UsuarioNoEncontrado usuarioNoEncontrado) {
            usuarioNoEncontrado.printStackTrace();
        } catch (NoEsAdministrativo noEsAdministrativo) {
            noEsAdministrativo.printStackTrace();
        } catch (CuentaNoEncontradaException e) {
            e.printStackTrace();
        } catch (DivisaNoCoincide divisaNoCoincide) {
            divisaNoCoincide.printStackTrace();
        } catch (MismaCuentaOrigenYDestino mismaCuentaOrigenYDestino) {
            mismaCuentaOrigenYDestino.printStackTrace();
        } catch (SaldoInsuficiente saldoInsuficiente) {
            saldoInsuficiente.printStackTrace();
        } catch (TransaccionConCantidadIncorrecta transaccionConCantidadIncorrecta) {
            transaccionConCantidadIncorrecta.printStackTrace();
        } catch (CuentaDeBajaNoPuedeRegistrarTransaccion cuentaDeBajaNoPuedeRegistrarTransaccion) {
            cuentaDeBajaNoPuedeRegistrarTransaccion.printStackTrace();
        }
        return null;
    }

    public String darBajar (String iban){
        try{
            gestionCuentas.cierreCuenta(sesion.getUsuario(), iban);
            return "cuentas.xhtml";
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
