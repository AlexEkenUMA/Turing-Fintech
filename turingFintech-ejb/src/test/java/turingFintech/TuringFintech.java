package turingFintech;

import clases.ejb.GestionCuentas;
import clases.ejb.GestionClientes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.persistence.Query;

import clases.ejb.GestionUsuarios;
import clases.ejb.UsuariosEJB;
import clases.ejb.exceptions.*;
import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.turingFintech.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TuringFintech {
	
	private static final Logger LOG = Logger.getLogger(TuringFintech.class.getCanonicalName());

	private static final String CLIENTES_EJB = "java:global/classes/ClientesEJB";
	private static final String CUENTAS_EJB  = "java:global/classes/CuentasEJB";
	private static final String USUARIOS_EJB = "java:global/classes/UsuariosEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "turingFintechTest";
	
	private GestionClientes gestionClientes;
	private GestionCuentas gestionCuentas;
	private GestionUsuarios gestionUsuarios;


	@Before
	public void setup() throws NamingException  {
		gestionClientes = (GestionClientes) SuiteTest.ctx.lookup(CLIENTES_EJB);
		gestionCuentas  = (GestionCuentas) SuiteTest.ctx.lookup(CUENTAS_EJB);
		gestionUsuarios =  (GestionUsuarios) SuiteTest.ctx.lookup(USUARIOS_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
	public void testtest(){

	}

	@Test
	@Requisitos("RF1")
	public void testUsuarioCorrecto(){
		Usuario usuario2 = new Usuario("Chikano", "Mascarilla", false);
		try{
			gestionUsuarios.usuarioCorrecto(usuario2);

		}catch (UsuarioNoEncontrado e){
			fail("No deberia lanzar la excepcion");
		}
		catch (EmpresaNoTieneAcceso e){
			fail("No deberia lanzar la excepcion");
		}
	}

	@Test
	@Requisitos("RF1")
	public void testUsuarioNoEncontrado(){
		Usuario usuario1 = new Usuario("JoseP", "1234", false);

		assertThrows(UsuarioNoEncontrado.class, () -> gestionUsuarios.usuarioCorrecto(usuario1));
	}

	@Test
	@Requisitos("RF1")
	public void testUsuarioAdministrativo(){
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		try{
			gestionUsuarios.usuarioAdministrativo(usuario1);
		}catch (UsuarioNoEncontrado e){
			fail("Usuario no encontrado (NO deberia)");
		}catch (NoEsAdministrativo e){
			fail("Usuario no administrativo (NO deberia)");
		}
	}

	@Test
	@Requisitos("RF1")
	public void testUsuarioNoAdministrativo(){

		Usuario usuario2 = new Usuario("Chikano", "Mascarilla", false);
		assertThrows(NoEsAdministrativo.class, () -> gestionUsuarios.usuarioAdministrativo(usuario2));
	}

	@Test
	@Requisitos("RF2")
	public void darDeAltaClienteNoValido(){

		// (Long id, String tipoCliente, String RazonSocial, String nombre, String apellidos,
		//                          Date fechaNac, String direccion, int codigoPostal, String pais, List<Autorizado> au, String ciudad)
		final String tipo = "Fisiiico";
		Date date = new Date();
		List<Autorizado> au = new ArrayList<>();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		assertThrows(ClienteNoValidoException.class, () -> gestionClientes.darAlta2(usuario1,32L, tipo, "Razon", "Nombre", "Apellidos", date,
				"Direccion", 2967, "Pais",au, "Ciudad" ));

	}

	@Test
	@Requisitos("RF2")
	public void darDeAltaClienteFisico(){

		final String tipo = "Fisica";
		Date date = new Date();
		List<Autorizado> au = new ArrayList<>();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);

		try{
			gestionClientes.darAlta2(usuario1,30L, tipo, "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){
			fail("ClienteNoValido (NO DEBERIA");
		} catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado (NO DEBERIA)");
		} catch (NoEsAdministrativo e) {
			fail("No es administrativo (NO DEBERIA)");
		}
		List<PersonaFisica> personaFisicas = gestionClientes.getPersonasFisicas();

		//assertEquals(1, personaFisicas.size());


		boolean ok = false;
		for (PersonaFisica pf : personaFisicas){
			if (pf.getIdentificacion() == 30L){
				ok = true;
			}
		}
		assertEquals(true, ok);


	}


	@Test
	@Requisitos("RF2")
	public void darDeAltaClienteJuridico(){

		final String tipo = "Juridico";
		Date date = new Date();
		List<Autorizado> au = new ArrayList<>();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		try{
			gestionClientes.darAlta2(usuario1,31L, tipo, "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){
			fail("ClienteNoValido (NO DEBERIA");
		}  catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado (NO DEBERIA)");
		} catch (NoEsAdministrativo e) {
			fail("No es administrativo (NO DEBERIA)");
		}
		List<PersonaJuridica> personaJuridicas = gestionClientes.getPersonasJuridicas();
		boolean ok = false;
		for (PersonaJuridica pj : personaJuridicas){
			if (pj.getIdentificacion() == 31L){
				ok = true;
			}
		}
		assertEquals(true, ok);
	}

	@Test
	@Requisitos("RF3")
	public void modificarClienteNoEncontrado(){
		Date date1 = new Date();
		Date date2 = new Date();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		Cliente cliente = new Cliente(12L, 1L, "juridico", "activo", date1, date2, "calle", "ciudad", 2900, "pais");
		assertThrows(ClienteNoEncontradoException.class, () -> gestionClientes.modificarCliente(usuario1, cliente,12L));
	}


	@Test
	@Requisitos("RF3")
	public void modificarCliente(){
		Long identificacion = 21L;
		PersonaJuridica pj2 = null;
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		List<PersonaJuridica> clientes = gestionClientes.getPersonasJuridicas();
		System.out.println(clientes);
		for(PersonaJuridica pj: clientes){
			if(identificacion == pj.getIdentificacion()){
				pj2 = pj;
				System.out.println("encontrado identificacion");
			}
		}
		PersonaJuridica copiapj2 = pj2;
		copiapj2.setCiudad("mlg");
		try{
			gestionClientes.modificarCliente(usuario1,copiapj2,21L);
		}catch (ClienteNoEncontradoException e){
			fail("No se ha encontrado cliente (NO DEBERIA)");
		} catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado usuario (NO DEBERIA)");
		} catch (NoEsAdministrativo e) {
			fail("Usuario no es administrativo (NO DEBERIA)");
		} catch (ModificarClienteDistintaID e){
			fail("Cliente con distinta ID (NO DEBERIA)");
		}
		List<PersonaJuridica> ClienteComprobar = gestionClientes.getPersonasJuridicas();
		for(PersonaJuridica pj: ClienteComprobar){
			if(identificacion == pj.getIdentificacion()){
				assertEquals(pj.getCiudad(), "mlg");
			}
		}
	}


	@Test
	@Requisitos("RF5")
	public void testDardeAltaCuentaTipoNoValidoException(){

		final String tipo = "poooled";
		Date date = new Date();
		List<Autorizado> au = new ArrayList<>();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		try{
			gestionClientes.darAlta2(usuario1,40L, "Fisica", "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){
			fail("Cliente no valido (NO DEBERIA)");
		}  catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado (NO DEBERIA)");
		} catch (NoEsAdministrativo e) {
			fail("No es administrativo (NO DEBERIA)");
		}
		List<PersonaFisica> pf = gestionClientes.getPersonasFisicas();
		List<DepositadaEn> dpList = new ArrayList<>();
		assertThrows(TipoNoValidoException.class, () -> gestionCuentas.aperturaCuenta(usuario1, pf.get(0),
				"123456", "789", tipo, dpList));
	}

	@Test
	@Requisitos("RF5")
	public void testDarDeAltaCuentaPooled(){

		final String tipo = "Pooled";
		Date date = new Date();
		List<Autorizado> au = new ArrayList<>();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);


		try{
			gestionClientes.darAlta2(usuario1,33L, "Fisica", "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){
			fail("Cliente no valido (NO DEBERIA)");
		}  catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado (NO DEBERIA)");
		} catch (NoEsAdministrativo e) {
			fail("No es administrativo (NO DEBERIA)");
		}
		List<PersonaFisica> personaFisica = gestionClientes.getPersonasFisicas();
		DepositadaEn dp = new DepositadaEn(0.00);
		CuentaReferencia cr = new CuentaReferencia("12345", "4567", "Santander", "sucursal",
				"pais", 0.0, date, true);
		dp.setCuentaReferencia(cr);
		List<DepositadaEn> dpList = new ArrayList<>();
		dpList.add(dp);

		try{
			gestionCuentas.aperturaCuenta(usuario1, personaFisica.get(0), "1234", "567", tipo, dpList);
			List<PooledAccount> pooledAccountList = gestionCuentas.obtenerCuentasPooled();
			boolean ok = false;
			for (PooledAccount pa : pooledAccountList){
				if (pa.getIBAN().equals("1234")){
					ok = true;
				}
			}
			assertEquals(true, ok);

		}catch (TuringTestException e){
			fail("No deberia lanzar excepcion");
		}
	}

	@Test
	@Requisitos("RF5")
	public void testDarDeAltaCuentaSegregada(){

		final String tipo = "Segregada";
		Date date = new Date();
		List<Autorizado> au = new ArrayList<>();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);

		try{
			gestionClientes.darAlta2(usuario1,34L, "Fisica", "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){
			fail("Cliente no valido (NO DEBERIA)");
		} catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado (NO DEBERIA)");
		} catch (NoEsAdministrativo e) {
			fail("No es administrativo (NO DEBERIA)");
		}
		List<PersonaFisica> personaFisica = gestionClientes.getPersonasFisicas();
		DepositadaEn dp = new DepositadaEn(0.00);
		CuentaReferencia cr = new CuentaReferencia("12345", "4567", "Santander", "sucursal",
				"pais", 0.0, date, true);
		dp.setCuentaReferencia(cr);
		List<DepositadaEn> dpList = new ArrayList<>();
		dpList.add(dp);

		try{
			gestionCuentas.aperturaCuenta(usuario1, personaFisica.get(0), "1234", "567", tipo, dpList);
			List<Segregada> segregadas = gestionCuentas.obtenerCuentasSegregada();
			boolean ok = false;
			for (Segregada s : segregadas){
				if (s.getIBAN().equals("1234")){
					ok = true;
				}
			}
			assertEquals(true, ok);


		}catch (TuringTestException e){
			fail("No deberia lanzar excepcion");
		}
	}

	@Test
	@Requisitos("RF9")
	public void testCierreCuentaNoEncontrada(){
		final String IBAN = "1234";
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);

		assertThrows(CuentaNoEncontradaException.class, () -> gestionCuentas.cierreCuenta(usuario1, IBAN));

	}


	@Test
	@Requisitos("RF9")
	public void testCierreCuenta() {

		final String tipo = "Pooled";
		final String IBAN = "12345";

		Date date = new Date();
		List<Autorizado> au = new ArrayList<>();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);

		try {
			gestionClientes.darAlta2(usuario1, 39L, "Fisica", "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais", au, "Ciudad");
		} catch (ClienteNoValidoException e) {
			fail("Cliente no valido (NO DEBERIA)");
		} catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado (NO DEBERIA)");
		} catch (NoEsAdministrativo e) {
			fail("No es administrativo (NO DEBERIA)");
		}
		List<PersonaFisica> personaFisica = gestionClientes.getPersonasFisicas();
		DepositadaEn dp = new DepositadaEn(0.00);
		CuentaReferencia cr = new CuentaReferencia("123456", "4567", "Santander", "sucursal",
				"pais", 0.0, date, true);
		dp.setCuentaReferencia(cr);
		List<DepositadaEn> dpList = new ArrayList<>();
		dpList.add(dp);

		try {
			gestionCuentas.aperturaCuenta(usuario1, personaFisica.get(0), IBAN, "567", tipo, dpList);
			gestionCuentas.cierreCuenta(usuario1, IBAN);
			List<PooledAccount> pooledAccountList = gestionCuentas.obtenerCuentasPooled();
			boolean ok = false;
			for (PooledAccount pl : pooledAccountList){
				if (pl.getIBAN().equals(IBAN)){
					assertEquals(false, pl.isEstado());
				}
			}
		} catch (TuringTestException e) {
			fail("No deberia lanzar excepcion");
		}
	}

	@Test
	@Requisitos("RF10")
	public void testAccesoPersonaJuridica(){
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);

		assertThrows(EmpresaNoTieneAcceso.class, () -> gestionUsuarios.usuarioCorrecto(usuario1));
	}

	@Test
	@Requisitos("RF11")
	public void testObtenerClientesHolanda(){
		final Long identificacion = 21L;
		List<Cliente> informeHolanda = gestionClientes.getClientesHolanda();
		assertEquals(informeHolanda.get(0).getIdentificacion(), identificacion);
	}

	@Test
	@Requisitos("RF11")
	public void testObtenerCuentasHolanda(){
		final String IBAN = "ES2057883234722030876293";
		List<Segregada> informeHolanda = gestionCuentas.getCuentasHolanda();
		assertEquals(informeHolanda.get(0).getIBAN(), IBAN);
	}
}
