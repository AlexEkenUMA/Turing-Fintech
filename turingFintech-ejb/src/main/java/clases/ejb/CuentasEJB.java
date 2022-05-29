package clases.ejb;

import clases.ejb.exceptions.*;
import es.uma.turingFintech.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Stateless
public class CuentasEJB implements GestionCuentas {

    @PersistenceContext(unitName = "turingFintech-ejb")
    private EntityManager em;

    @EJB GestionUsuarios gestionUsuarios;

    @Override
    public void aperturaCuenta(Usuario u,Cliente cliente,String IBAN,String SWIFT, String tipo, List<DepositadaEn> dpList)
            throws TipoNoValidoException, UsuarioNoEncontrado, NoEsAdministrativo {

        gestionUsuarios.usuarioAdministrativo(u);

        if (!tipo.equals("Pooled") && !tipo.equals("Segregada")){
            throw new TipoNoValidoException();
        }
        Date fecha = new Date();
        if (tipo.equals("Pooled")) {
            PooledAccount pooledAccount = new PooledAccount(IBAN, SWIFT, fecha, "Activa", tipo);
           // em.persist(cliente);

            for (DepositadaEn dp : dpList){
                if (dp != null) {
                    dp.setPooledAccount(pooledAccount);
                    em.persist(dp);
                    //em.persist(dp.getCuentaReferencia());
                  CuentaReferencia cuentaReferencia =   em.find(CuentaReferencia.class, dp.getCuentaReferencia().getIBAN());
                    if (cuentaReferencia == null){
                        em.persist(dp.getCuentaReferencia());
                    }
                }
            }
            pooledAccount.setCliente(cliente);
            pooledAccount.setListaDepositos(dpList);
            em.persist(pooledAccount);
        }
        if (tipo.equals("Segregada")){
            Segregada segregada = new Segregada(IBAN, SWIFT, fecha, "Activa", tipo,0.00);
            segregada.setCliente(cliente);
            em.persist(dpList.get(0).getCuentaReferencia());
            segregada.setCr(dpList.get(0).getCuentaReferencia());
            em.persist(segregada);
        }
    }

    @Override
    public void aperturaCuentaPooled(Usuario u,Cliente cliente,String IBAN,String SWIFT, List<DepositadaEn> dpList) throws UsuarioNoEncontrado, NoEsAdministrativo{
        gestionUsuarios.usuarioAdministrativo(u);

        PooledAccount pooledAccount = new PooledAccount(IBAN, SWIFT, new Date(), "Activa", "Pooled");
        //habria que comprobar si el cliente existe
        pooledAccount.setCliente(cliente);

        //comprobamos que todas las lista de depositada en pertenecen a la misma cuenta referencia, ya que las cuentas
        //pooled comparten el saldo en la misma cuenta referencia.

        for (DepositadaEn dp : dpList){
            if (dp != null) {
                em.persist(dp);
            }
        }
        pooledAccount.setListaDepositos(dpList);
        em.persist(pooledAccount);
    }


    @Override
    public void aperturaCuentaSegregada(Usuario u,Cliente cliente,String IBAN,String SWIFT, CuentaReferencia cr) throws UsuarioNoEncontrado, NoEsAdministrativo{
        gestionUsuarios.usuarioAdministrativo(u);
        //hay que preguntar la comision por defecto de una cuenta segregada
        Segregada segregada = new Segregada(IBAN, SWIFT, new Date(), "Activa", "Segregada",0.00);
        CuentaReferencia crExiste = em.find(CuentaReferencia.class, cr.getIBAN());
        if(crExiste == null){
            cr.setSegregada(segregada);
            em.persist(cr);
            segregada.setCr(cr);
        }
        else{
            crExiste.setSegregada(segregada);
            segregada.setCr(crExiste);
        }
        segregada.setCliente(cliente);
        em.persist(segregada);
    }


    @Override
    public void cierreCuenta (Usuario u, String IBAN)
            throws CuentaNoEncontradaException, SaldoIncorrectoException, UsuarioNoEncontrado, NoEsAdministrativo
    {
        gestionUsuarios.usuarioAdministrativo(u);

        Date fecha = new Date();
        CuentaFintech cuentaEntity = em.find(CuentaFintech.class, IBAN);
        if (cuentaEntity == null){
            throw new CuentaNoEncontradaException();
        }
        if (cuentaEntity.getTipo().equals("Pooled")){
            PooledAccount pooledEntity = em.find(PooledAccount.class, IBAN);
            if (pooledEntity == null){
                throw new CuentaNoEncontradaException();
            }
            List<DepositadaEn> dpList = pooledEntity.getListaDepositos();
            boolean ok = true;
            for (DepositadaEn dp : dpList){
                if (dp.getSaldo() != 0){
                    ok = false;
                }
            }
        if (!ok){
            throw new SaldoIncorrectoException();
        }
        pooledEntity.setEstado("Cerrada");
        pooledEntity.setFecha_cierre(fecha);
        em.merge(pooledEntity);
        }
        if (cuentaEntity.getTipo().equals("Segregada")){
            Segregada segregadaEntity = em.find(Segregada.class, IBAN);
            if (segregadaEntity == null){
                throw new CuentaNoEncontradaException();
            }
            if (segregadaEntity.getCr().getSaldo() != 0){
                throw new SaldoIncorrectoException();
            }
            segregadaEntity.setFecha_cierre(fecha);
            segregadaEntity.setEstado("Cerrada");
            em.merge(segregadaEntity);
        }
    }

    @Override
    public void darAltaRef(Usuario u, CuentaReferencia r) throws UsuarioNoEncontrado, NoEsAdministrativo {
        gestionUsuarios.usuarioAdministrativo(u);
        em.persist(r);
    }

    @Override
    public void setDepositos(String pooledAccount, String ibanCr, String abreviaturaDiv, Double saldo) throws CuentaNoEncontradaException, DivisaNoCoincide {

        CuentaReferencia cuentaReferencia = em.find(CuentaReferencia.class, ibanCr);
        if (cuentaReferencia == null){
            throw new CuentaNoEncontradaException();
        }
        PooledAccount pooledAccount1 = em.find(PooledAccount.class,pooledAccount);
        if (pooledAccount1 == null ){
            throw new CuentaNoEncontradaException();
        }
        if (!cuentaReferencia.getDivisa().getAbreviatura().equals(abreviaturaDiv)){
            throw new DivisaNoCoincide();
        }
        if (cuentaReferencia.getSaldo() < saldo){
           // throw new SaldoInsuficiente();
        }

        DepositadaEn depositadaEn = new DepositadaEn(saldo);

        depositadaEn.setPooledAccount(pooledAccount1);
        depositadaEn.setCuentaReferencia(cuentaReferencia);

        em.persist(depositadaEn);

        List<DepositadaEn> depositadaEns = pooledAccount1.getListaDepositos();
        depositadaEns.add(depositadaEn);


    }

    @Override
    public PooledAccount getCuentaPooled (String iban) throws CuentaNoEncontradaException {
        List<PooledAccount> cuentas;
        Query query = em.createQuery("select cuenta from PooledAccount cuenta where cuenta.IBAN = :iban");
        query.setParameter("iban", iban);
        cuentas = (List<PooledAccount>) query.getResultList();
        if (cuentas.size() == 0){
            throw new CuentaNoEncontradaException();
        }
        return cuentas.get(0);
    }

    @Override
    public Cuenta getCuenta (String iban) throws CuentaNoEncontradaException {
        List<Cuenta> cuentas;
        Query query = em.createQuery("select cuenta from Cuenta cuenta where cuenta.IBAN = :iban");
        query.setParameter("iban", iban);
        cuentas = (List<Cuenta>) query.getResultList();
        if (cuentas.size() == 0){
            throw new CuentaNoEncontradaException();
        }
        return cuentas.get(0);
    }


    @Override
    public List<CuentaFintech> getCuentasCliente (Long id){
        List<CuentaFintech> cuentaFintechList;
        Query query = em.createQuery("select cuenta from CuentaFintech cuenta where cuenta.cliente.id = :cliente");
        query.setParameter("cliente", id);
        cuentaFintechList = (List<CuentaFintech>) query.getResultList();
        return cuentaFintechList;
    }



    @Override
    public List<Divisa> getDivisas(){
        List<Divisa> divisas;
        Query query = em.createQuery("select divisa from Divisa divisa");
        divisas = (List<Divisa>) query.getResultList();
        return divisas;
    }
    @Override
    public List<Cuenta> getCuentas(){
        List<Cuenta> cuentas;
        Query query = em.createQuery("select cuenta from Cuenta cuenta");
        cuentas = (List<Cuenta>) query.getResultList();
        return cuentas;
    }


    @Override
    public List<CuentaReferencia> obtenerReferencias (){
        List<CuentaReferencia> cuentaReferenciaList;
        Query query = em.createQuery("select cuenta from CuentaReferencia cuenta");
        cuentaReferenciaList = (List<CuentaReferencia>) query.getResultList();
        return cuentaReferenciaList;
    }

    @Override
    public CuentaFintech obtenerCuentasFintechIban (String iban) throws CuentaNoEncontradaException {
        List<CuentaFintech> fintechList;
        Query query = em.createQuery("select cuenta from CuentaFintech cuenta where cuenta.IBAN = :iban");
        query.setParameter("iban", iban);
        fintechList = (List<CuentaFintech>) query.getResultList();
        if (fintechList.isEmpty()){
            throw new CuentaNoEncontradaException();
        }
        return fintechList.get(0);
    }

    @Override
    public Cuenta obtenerCuentaIban (String iban) throws CuentaNoEncontradaException {
        List<Cuenta> cuentaList;
        Query query = em.createQuery("select cuenta from Cuenta cuenta where cuenta.IBAN = :iban");
        query.setParameter("iban", iban);
        cuentaList = (List<Cuenta>) query.getResultList();
        if (cuentaList.isEmpty()){
            throw new CuentaNoEncontradaException();
        }
        return cuentaList.get(0);
    }

    @Override
    public List<CuentaFintech> obtenerCuentasFintech (){
        List<CuentaFintech> fintechList;
        Query query = em.createQuery("select cuenta from CuentaFintech cuenta");
        fintechList = (List<CuentaFintech>) query.getResultList();
        return fintechList;
    }

    @Override
    public List<PooledAccount> obtenerCuentasPooled (){

        List<PooledAccount> pooled;
        Query query = em.createQuery("select cuenta from PooledAccount cuenta");
        pooled = (List<PooledAccount>) query.getResultList();
        return pooled;
    }

    @Override
    public List<Segregada> obtenerCuentasSegregada(){
        List<Segregada> segregadas;
        Query query = em.createQuery("select cuenta from Segregada cuenta");
        segregadas = (List<Segregada>) query.getResultList();
        return segregadas;
    }

    public Divisa obetenerDivisa(String abre) throws DivisaNoCoincide {
        List<Divisa> divisas;
        Query query = em.createQuery("select divisa from Divisa divisa where divisa.abreviatura = :abre");
        query.setParameter("abre", abre);
        divisas = (List<Divisa>) query.getResultList();
        if (divisas.isEmpty()){
            throw  new DivisaNoCoincide();
        }
        return divisas.get(0);
    }

    @Override
    public List<Segregada> getCuentasHolanda(String estado, String IBAN) throws NoEsAdministrativo, UsuarioNoEncontrado, NingunaCuentaCoincideConLosParametrosDeBusqueda{
        Query query = em.createQuery("SELECT s FROM Segregada s where s.estado like :estado" +
                " and s.IBAN like :IBAN " );

        if (estado != null && !estado.isEmpty()){
            query.setParameter("estado" , estado);
        }else{
            query.setParameter("estado", "%");
        }
        if (IBAN != null && !IBAN.isEmpty()){
            query.setParameter("IBAN" , IBAN);
        }else{
            query.setParameter("IBAN", "%");
        }

        List<Segregada> listaResultado = query.getResultList();
        if(listaResultado.isEmpty()){
            throw new NingunaCuentaCoincideConLosParametrosDeBusqueda();
        }
        return listaResultado;
    }

    @Override
    public String getInformeInicialAlemania(Usuario u) throws UsuarioNoEncontrado, NoEsAdministrativo, IOException {
        gestionUsuarios.usuarioAdministrativo(u);
        Query query = em.createQuery("SELECT s FROM Segregada s");
        List<Segregada> lista = query.getResultList();
        SimpleDateFormat sdf=new SimpleDateFormat("ddMMYYYYhhmmss");
        String dateString=sdf.format(new Date());
        String nombreArchivo = "C:/Turing_IBAN_" + dateString + ".csv";
        try(
                BufferedWriter stringWriter = Files.newBufferedWriter(Paths.get(nombreArchivo));
                CSVPrinter csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name", "First_Name",
                        "Street", "City", "Post_Code", "Country", "identification_Number", "Date_Of_Birth"));

        ){

            csvPrinter.print("Ebury_IBAN_" + dateString + "\n");
            for(Segregada s : lista){
                if(s.getCliente() instanceof PersonaJuridica){
                    PersonaJuridica pj = (PersonaJuridica) s.getCliente();
                    for(Autorizado au : pj.getAutorizados()){
                        if(au.getUsuario().getCliente() instanceof PersonaFisica){
                            PersonaFisica pf = (PersonaFisica) au.getUsuario().getCliente();
                            csvPrinter.printRecord(s.getIBAN(), pf.getApellidos(), pf.getNombre(), pf.getDireccion(),
                                    pf.getCodigo_Postal(), pf.getPais(), pf.getId(), pf.getFecha_Nacimiento().toString());
                        }
                    }
                }
                if(s.getCliente() instanceof PersonaFisica){
                    PersonaFisica pf = (PersonaFisica) s.getCliente();
                    csvPrinter.printRecord(s.getIBAN(), pf.getApellidos(), pf.getNombre(), pf.getDireccion(),
                    pf.getCodigo_Postal(), pf.getPais(), pf.getId(), pf.getFecha_Nacimiento().toString());
                }

            }
            csvPrinter.flush();
        }
        return nombreArchivo;
    }

    @Override
    public String getInformeSemanalAlemania(Usuario u) throws UsuarioNoEncontrado, NoEsAdministrativo, IOException {
        gestionUsuarios.usuarioAdministrativo(u);
        Query query = em.createQuery("SELECT s FROM Segregada s where s.estado='Activa'");
        List<Segregada> lista = query.getResultList();
        SimpleDateFormat sdf=new SimpleDateFormat("ddMMYYYYhhmmss");
        String dateString=sdf.format(new Date());
        String nombreArchivo = "C:/Turing_IBAN_" + dateString + ".csv";
        try(
                BufferedWriter stringWriter = Files.newBufferedWriter(Paths.get(nombreArchivo));
                CSVPrinter csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name", "First_Name",
                        "Street", "City", "Post_Code", "Country", "identification_Number", "Date_Of_Birth"));

        ){
            csvPrinter.print("Ebury_IBAN_" + dateString + "\n");
            for(Segregada s : lista){
                if(s.getCliente() instanceof PersonaJuridica){
                    PersonaJuridica pj = (PersonaJuridica) s.getCliente();
                    for(Autorizado au : pj.getAutorizados()){
                        if(au.getEstado().equals("Activo")){
                            if(au.getUsuario().getCliente() instanceof PersonaFisica){
                                PersonaFisica pf = (PersonaFisica) au.getUsuario().getCliente();
                                if(pf.getEstado().equals("Activo")){
                                    csvPrinter.printRecord(s.getIBAN(), pf.getApellidos(), pf.getNombre(), pf.getDireccion(),
                                            pf.getCodigo_Postal(), pf.getPais(), pf.getId(), pf.getFecha_Nacimiento().toString());
                                }
                            }
                        }
                    }
                }
                if(s.getCliente() instanceof PersonaFisica){
                    PersonaFisica pf = (PersonaFisica) s.getCliente();
                    if(pf.getEstado().equals("Activo")){
                        csvPrinter.printRecord(s.getIBAN(), pf.getApellidos(), pf.getNombre(), pf.getDireccion(),
                                pf.getCodigo_Postal(), pf.getPais(), pf.getId(), pf.getFecha_Nacimiento().toString());
                    }

                }

            }
            csvPrinter.flush();

        }
        return nombreArchivo;
    }




}



