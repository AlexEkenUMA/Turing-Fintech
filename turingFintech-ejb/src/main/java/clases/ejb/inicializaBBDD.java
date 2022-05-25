package clases.ejb;

import es.uma.turingFintech.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Singleton
@Startup
public class inicializaBBDD {

    @PersistenceContext(unitName = "turingFintech-ejb")
    private EntityManager em;

    @PostConstruct
    public void inicializar(){

        Divisa comprobacion = em.find(Divisa.class, "USD");
        if (comprobacion != null){
            return;
        }

        //Clientes

        Date date = new Date();
        PersonaJuridica pj1 = new PersonaJuridica(null, "P3310693A", "Juridico", "Activo", date, null, "ETSI",
                "MALAGA", 29640, "ESPAÑA", "GANAR_DINEROS");

        PersonaFisica pf1 = new PersonaFisica( null, "63937528N", "Fisico", "Activo", date, null,
                "Avnd Ing", "Mijas", 29649, "España", "Jose", "Pan", date);

        Autorizado au1 = new Autorizado(null, "Y4001267V", "Alejandro", "Requena",
                "Directo", date, "Activo", date, null);

        List<Autorizado> autorizadoList = new ArrayList<>();
        autorizadoList.add(au1);
        List<PersonaJuridica> personaJuridicaList = new ArrayList<>();
        personaJuridicaList.add(pj1);
        au1.setEmpresas(personaJuridicaList);
        pj1.setAutorizados(autorizadoList);

        em.persist(pj1);
        em.persist(pf1);
        em.persist(au1);

        //Divisas

        Divisa dolares = new Divisa("USD", "Dolar", '$', 0.95);
        Divisa euros   = new Divisa("EUR", "Euro", '€', 1);
        Divisa libras  = new Divisa("GBP", "Libra", '£', 1.18);

        em.persist(dolares);
        em.persist(euros);
        em.persist(libras);

        //Cuentas

        CuentaReferencia cr1 = new CuentaReferencia("VG57DDVS5173214964983931", null, "Ebury", "Malaga", "España", 500.00,
                date, true);
        cr1.setDivisa(dolares);

        CuentaReferencia cr2 = new CuentaReferencia("HN47QUXH11325678769785549996", null, "Ebury", "Malaga",
                "España", 1000.0, date, true);
        cr1.setDivisa(dolares);



        CuentaReferencia cr3 = new CuentaReferencia("ES7121007487367264321882", null, "Ebury", "Malaga",
                "España", 100.0, date, true);
        cr3.setDivisa(euros);

        CuentaReferencia cr4 = new CuentaReferencia("VG88HBIJ4257959912673134", null, "Ebury", "Malaga",
                "España", 200.0, date, true);
        cr4.setDivisa(dolares);
        CuentaReferencia cr5 = new CuentaReferencia("GB79BARC20040134265953", null, "Ebury", "Malaga",
                "España", 134.0, date, true);
        cr5.setDivisa(libras);

        em.persist(cr1);
        em.persist(cr2);
        em.persist(cr3);
        em.persist(cr4);
        em.persist(cr5);

        Segregada segregada1 = new Segregada("NL63ABNA6548268733", null, date, "Activa", "Segregada",0.0);
        segregada1.setCr(cr1);
        segregada1.setCliente(pj1);
        em.persist(segregada1);
        List<CuentaFintech> cuentaFinteches = new ArrayList<>();
        cuentaFinteches.add(segregada1);
        pj1.setCuentasFintech(cuentaFinteches);
        cr1.setSegregada(segregada1);


        em.merge(cr1);
        em.merge(pj1);


        Segregada segregada2 = new Segregada("FR5514508000502273293129K55", null, date, "Activa", "Segregada", 0.0);
        segregada2.setCr(cr2);
        segregada2.setCliente(pj1);
        em.persist(segregada2);
        List<CuentaFintech> cuentasFintech = pj1.getCuentasFintech();
        cuentasFintech.add(segregada2);
        cr2.setSegregada(segregada2);

        em.merge(cr2);
        em.merge(pj1);

        Segregada segregada3 = new Segregada("DE31500105179261215675", null, date, "Cerrada", "Segregada", 0.0);
        Date fecha_cierre = new GregorianCalendar(2021, Calendar.SEPTEMBER, 1).getTime();
        segregada3.setFecha_cierre(fecha_cierre);
        segregada3.setCliente(pj1);
        em.persist(segregada3);

        List<CuentaFintech> cuentasFintechh = pj1.getCuentasFintech();
        cuentasFintechh.add(segregada3);

        em.merge(pj1);

        //Cuentas pooled
        PooledAccount pooledAccount = new PooledAccount("ES8400817251647192321264", null, date, "Activa", "Pooled");
        pooledAccount.setCliente(pj1);
        DepositadaEn depositadaEn1 = new DepositadaEn(100);
        depositadaEn1.setPooledAccount(pooledAccount);
        depositadaEn1.setCuentaReferencia(cr3);
        em.persist(depositadaEn1);
        List<DepositadaEn> cr3List = new ArrayList<>();
        cr3List.add(depositadaEn1);
        cr3.setListaDepositos(cr3List);
        List<DepositadaEn> depositadaEns = new ArrayList<>();
        depositadaEns.add(depositadaEn1);

        DepositadaEn depositadaEn2 = new DepositadaEn(200);
        depositadaEn2.setPooledAccount(pooledAccount);
        depositadaEn2.setCuentaReferencia(cr4);
        em.persist(depositadaEn2);
        List<DepositadaEn> cr4List = new ArrayList<>();
        cr4List.add(depositadaEn2);
        cr4.setListaDepositos(cr4List);
        depositadaEns.add(depositadaEn2);

        DepositadaEn depositadaEn3 = new DepositadaEn(134);
        depositadaEn3.setPooledAccount(pooledAccount);
        depositadaEn3.setCuentaReferencia(cr5);
        em.persist(depositadaEn3);
        List<DepositadaEn> cr5List = new ArrayList<>();
        cr5List.add(depositadaEn3);
        cr5.setListaDepositos(cr5List);
        depositadaEns.add(depositadaEn3);

        pooledAccount.setListaDepositos(depositadaEns);


        em.merge(cr3);
        em.merge(cr4);
        em.merge(cr5);

        em.persist(pooledAccount);

        //Transacción


        Transaccion transaccion1 = new Transaccion(null, date, 200.00, "Alquiler", "Chikano", "Transferencia");
        transaccion1.setOrigen(segregada1);
        transaccion1.setDestino(pooledAccount);
        transaccion1.setEmisor(dolares);
        transaccion1.setReceptor(dolares);

        em.persist(transaccion1);



        //Usuarios

        Usuario juan = new Usuario("Juan", "Juan", false);
        juan.setCliente(pf1);

        Usuario ana = new Usuario("Ana", "Ana", false);
        ana.setAutorizado(au1);

        Usuario ponciano = new Usuario("ponciano", "ponciano", true);

        pf1.setUsuario(juan);
        au1.setUsuario(ana);

        em.merge(pf1);
        em.merge(au1);

        em.persist(juan);
        em.persist(ana);
        em.persist(ponciano);

    }

}

