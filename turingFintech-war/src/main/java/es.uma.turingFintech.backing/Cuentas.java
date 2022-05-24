package es.uma.turingFintech.backing;

import clases.ejb.GestionClientes;
import clases.ejb.GestionCuentas;
import clases.ejb.exceptions.CuentaNoEncontradaException;
import clases.ejb.exceptions.DivisaNoCoincide;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.turingFintech.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Named(value = "cuentas")
@RequestScoped
public class Cuentas {

    public static enum Modo {
        SEGREGADA,
        POOLED,
        NOACCION
    };

    String dni = "";

    String ibanCr = "";

    String divisa = "";

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
        modo = Modo.NOACCION;
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



    public String accion(){

        //public void aperturaCuentaSegregada(Usuario u,Cliente cliente,String IBAN,String SWIFT, CuentaReferencia cr)
        try{
            if (modo.equals(Modo.SEGREGADA)){
                asociarCliente(dni);
                asociarReferencia(ibanCr);
                gestionCuentas.aperturaCuentaSegregada(sesion.getUsuario(), segregada.getCliente(), segregada.getIBAN(),
                        segregada.getSWIFT(), segregada.getCr());
            }

            if (modo.equals((Modo.POOLED))){

                asociarCliente(dni);
               List<DepositadaEn> depositadaEns = new ArrayList<>();
                gestionCuentas.aperturaCuentaPooled(sesion.getUsuario(), pooledAccount.getCliente(), pooledAccount.getIBAN(), pooledAccount.getSWIFT()
                , depositadaEns);

                gestionCuentas.setDepositos(pooledAccount.getIBAN(), ibanCr, divisa);
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


}
