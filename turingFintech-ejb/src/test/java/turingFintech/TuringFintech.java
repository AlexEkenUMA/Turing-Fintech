package turingFintech;

import clases.ejb.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.persistence.Query;

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
	private static final String AUTORIZADOS_EJB = "java:global/classes/AutorizadosEJB";
	private static final String TRANSACCIONES_EJB = "java:global/classes/TransaccionesEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "turingFintechTest";
	
	private GestionClientes gestionClientes;
	private GestionCuentas gestionCuentas;
	private GestionUsuarios gestionUsuarios;
	private GestionAutorizados gestionAutorizados;
	private GestionTransacciones gestionTransacciones;


	@Before
	public void setup() throws NamingException  {
		gestionClientes    = (GestionClientes) SuiteTest.ctx.lookup(CLIENTES_EJB);
		gestionCuentas     = (GestionCuentas) SuiteTest.ctx.lookup(CUENTAS_EJB);
		gestionUsuarios    =  (GestionUsuarios) SuiteTest.ctx.lookup(USUARIOS_EJB);
		gestionAutorizados = (GestionAutorizados) SuiteTest.ctx.lookup(AUTORIZADOS_EJB);
		gestionTransacciones = (GestionTransacciones) SuiteTest.ctx.lookup(TRANSACCIONES_EJB);

		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
	public void testtest(){

	}

	@Test
	@Requisitos("RF1")
	public void testUsuarioCorrecto(){
		Usuario messi = new Usuario("Lionel Andrés Messi", "messirve", true);
		try{
			gestionUsuarios.usuarioCorrecto(messi);

		}catch (UsuarioNoEncontrado e){
			fail("No deberia lanzar la excepcion");
		}
		catch (EmpresaNoTieneAcceso e){
			fail("No deberia lanzar la excepcion");
		} catch (AutorizadoSoloTieneAccesoACuentasClienteBloqueado e) {
			fail("Autorizado que solo tiene acceso a las cuentas de clientes que estan bloqueados");
		} catch (PersonaFisicaBloqueada e) {
			fail("Persona fisica bloqueada esta intentando acceder a la aplicacion");
		} catch (AutorizadoBloqueado autorizadoBloqueado) {
			fail("Persona autorizada bloqueada esta intentando acceder a la aplicacion");
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
		assertThrows(ClienteNoValidoException.class, () -> gestionClientes.darDeAltaCliente(usuario1,"32L", tipo, "Razon", "Nombre", "Apellidos", date,
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
			gestionClientes.darDeAltaCliente(usuario1,"30L", tipo, "Razon", "Nombre", "Apellidos", date,
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
			if (pf.getIdentificacion().equals("30L")){
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
			gestionClientes.darDeAltaCliente(usuario1,"31L", tipo, "Razon", "Nombre", "Apellidos", date,
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
			if (pj.getIdentificacion().equals("31L")){
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
		Cliente cliente = new Cliente(12L, "1L", "Juridico", "activo", date1, date2, "calle", "ciudad", 2900, "pais");
		assertThrows(ClienteNoEncontradoException.class, () -> gestionClientes.modificarCliente(usuario1, cliente,"12L"));
	}


	@Test
	@Requisitos("RF3")
	public void modificarCliente(){
		String identificacion = "21L";
		PersonaJuridica pj2 = null;
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		List<PersonaJuridica> clientes = gestionClientes.getPersonasJuridicas();
		System.out.println(clientes);
		for(PersonaJuridica pj: clientes){
			if(identificacion.equals(pj.getIdentificacion())){
				pj2 = pj;
				System.out.println("encontrado identificacion");
			}
		}
		PersonaJuridica copiapj2 = pj2;
		copiapj2.setCiudad("mlg");
		try{
			gestionClientes.modificarCliente(usuario1,copiapj2,"21L");
		}catch (ClienteNoEncontradoException e){
			fail("No se ha encontrado cliente (NO DEBERIA)");
		} catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado usuario (NO DEBERIA)");
		} catch (NoEsAdministrativo e) {
			fail("Usuario no es administrativo (NO DEBERIA)");
		} catch (ModificarClienteDistintaID e){
			fail("Cliente con distinta ID (NO DEBERIA)");
		}
		catch (TipoNoValidoException e){
			fail("Tipo no valido exception lanzado (NO DEBERIA)");
		}

		List<PersonaJuridica> ClienteComprobar = gestionClientes.getPersonasJuridicas();
		for(PersonaJuridica pj: ClienteComprobar){
			if(identificacion.equals(pj.getIdentificacion())){
				assertEquals(pj.getCiudad(), "mlg");
			}
		}
	}

	@Test
	@Requisitos("RF4")
	public void testEliminarClienteNoExistente(){
		Date date1 = new Date();
		Date date2 = new Date();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		assertThrows(ClienteNoEncontradoException.class, () -> gestionClientes.eliminarCliente(usuario1, "12L"));
	}

	@Test
	@Requisitos("RF4")
	public void testEliminarCliente(){
		final String identificacion = "300L";
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		try{
			gestionClientes.eliminarCliente(usuario1, identificacion);
		} catch (CuentaActiva cuentaActiva) {
			fail("Este cliente tiene alguna cuenta activa todavía");
		} catch (UsuarioNoEncontrado usuarioNoEncontrado) {
			fail("El usuario no fue encontrado en la BBDD");
		} catch (ClienteNoEncontradoException e) {
			fail("El cliente no fue encontrado en la BBDD");
		} catch (NoEsAdministrativo noEsAdministrativo) {
			fail("El usuario que da de baja el cliente no es administrativo");
		}
		List<PersonaJuridica> ClienteComprobar = gestionClientes.getPersonasJuridicas();
		for(PersonaJuridica pj: ClienteComprobar){
			if(identificacion.equals(pj.getIdentificacion())){
				assertNotNull(pj.getFecha_Baja());
			}
		}
	}
	@Test
	@Requisitos("RF4")
	public void testEliminarClienteConCuentaActiva(){
		final String identificacion = "108L";
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		assertThrows(CuentaActiva.class, () -> gestionClientes.eliminarCliente(usuario1, identificacion));
	}

	@Test
	@Requisitos("RF5")
	public void testDarDeAltaCuentaPooled(){

		final String tipo = "Pooled";
		Date date = new Date();
		List<Autorizado> au = new ArrayList<>();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);


		try{
			gestionClientes.darDeAltaCliente(usuario1,"33L", "Fisica", "Razon", "Nombre", "Apellidos", date,
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
			gestionCuentas.aperturaCuentaPooled(usuario1, personaFisica.get(0), "1234", "567", dpList);
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
			gestionClientes.darDeAltaCliente(usuario1,"34L", "Fisica", "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){
			fail("Cliente no valido (NO DEBERIA)");
		} catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado (NO DEBERIA)");
		} catch (NoEsAdministrativo e) {
			fail("No es administrativo (NO DEBERIA)");
		}
		List<PersonaFisica> personaFisica = gestionClientes.getPersonasFisicas();
		CuentaReferencia cr = new CuentaReferencia("12345", "4567", "Santander", "sucursal",
				"pais", 0.0, date, true);
		try{
			gestionCuentas.aperturaCuentaSegregada(usuario1, personaFisica.get(0), "1234", "567", cr);
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
	@Requisitos("RF6")
	public void testAnadirAutorizadoPersonaJuridicaNoEncontrada(){
		Date date = new Date();
		Autorizado autorizado = new Autorizado(null, "10L", "Nombre", "Apellidos",
				"Direccion", date, "Estado", date, date);
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		PersonaJuridica personaJuridica1 = new PersonaJuridica(10L, "50L", "Juridico", "Activo", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		assertThrows(PersonaJuridicaNoEncontrada.class, () -> gestionAutorizados.anadirAutorizados(usuario1, personaJuridica1,
		autorizado));
	}

	@Test
	@Requisitos("RF6")
	public void testAnadirAutorizado(){
		Date date = new Date();
		Autorizado autorizado = new Autorizado(150L, "100L", "Nombre", "Apellidos",
				"Direccion", date, "Estado", date, date);
		List<PersonaJuridica> pjP = new ArrayList<>();
		autorizado.setEmpresas(pjP);
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		try{
			List<Autorizado> autor = new ArrayList<>();
			gestionClientes.darDeAltaCliente(usuario1,"50L", "Juridico", "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",autor, "Ciudad" );

			List<PersonaJuridica> personaJuridicas = gestionClientes.getPersonasJuridicas();
			Long id = null;
			PersonaJuridica personaJuridica1 = null;
			for (PersonaJuridica pj : personaJuridicas){
				if (pj.getIdentificacion().equals("50L")){
					id = pj.getId();
					personaJuridica1 = pj;
				}
			}
			gestionAutorizados.anadirAutorizados(usuario1, personaJuridica1, autorizado);
			for (PersonaJuridica pj : personaJuridicas){
				if (pj.getIdentificacion().equals("50L")){
					id = pj.getId();
					personaJuridica1 = pj;
				}
			}
			List<Autorizado> autorizadoList 	   = gestionAutorizados.getAutorizados();
			Autorizado autorizado1 			       = null;
			for (Autorizado au : autorizadoList){
				if (au.getIdentificacion() == "100L"){
					autorizado1 = au;
				}
			}
			List<Autorizado> auJuridcas = personaJuridica1.getAutorizados();
			List<PersonaJuridica> pjAuto = autorizado1.getEmpresas();
			boolean bautorizado = false;
			boolean bjuridica   = false;
			for (Autorizado au : auJuridcas){
				if (au.getIdentificacion() == "100L"){
					bautorizado = true;
				}
			}
			for (PersonaJuridica pj : pjAuto){
				if (pj.getIdentificacion().equals("50L")){
					bjuridica = true;
				}
			}

			assertEquals(true, bautorizado);
			assertEquals(true, bjuridica);


		}catch (NoEsAdministrativo e){
			fail("NoEsAdministrativo (NO DEBERIA)");
		}catch (UsuarioNoEncontrado e){
			fail("UsuarioNoEncontrado (NO DEBERIA)");
		}catch (PersonaJuridicaNoEncontrada e){
			fail("PersonaJuridicaNoEncontrada (NO DEBERIA)");
		}catch (ClienteNoValidoException e){
			fail("ClienteNoValido (NO DEBERIA)");
		}

	}

	@Test
	@Requisitos("RF7")
	public void testModificarAutorizadoNoEncontradoAutorizado(){
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		Date date1 = new Date();
		Date date2 = new Date();
		Autorizado autorizado = new Autorizado(1L, "232L", "Nombre", "Apellidos",
				"Direccion", date1, "Estado", date2, null);
		assertThrows(AutorizadoNoEncontradoException.class, () -> gestionAutorizados.modificarAutorizados(usuario1, autorizado, 36L));

	}

	@Test
	@Requisitos("RF7")
	public void testModificarAutorizadosConDistintaID(){
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		Date date1 = new Date();
		Date date2 = new Date();
		Autorizado autorizado = new Autorizado(5L, "36L", "Nombre", "Apellidos",
				"Direccion", date1, "Estado", date2, null);
		assertThrows(ModificarAutorizadosDistintaID.class, () -> gestionAutorizados.modificarAutorizados(usuario1, autorizado, 1L));

	}

	@Test
	@Requisitos("RF7")
	public void testmodificarAutorizados(){
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		Date date1 = new Date();
		Date date2 = new Date();
		Autorizado autorizado = new Autorizado(5L, "36L", "Nombre", "Apellidos",
				"Direccion", date1, "Estado", date2, null);
		try{
			gestionAutorizados.modificarAutorizados(usuario1,autorizado,5L);
		} catch (AutorizadoNoEncontradoException e) {
			fail("No se ha encontrado persona autorizada");
		} catch (UsuarioNoEncontrado e) {
			fail("No se ha encontrado usuario");
		} catch (NoEsAdministrativo e) {
			fail("Usuario no es administrativo");
		} catch (ModificarAutorizadosDistintaID e) {
			fail("Autorizado con distinta ID");
		}
		List<Autorizado> autorizadoList = gestionAutorizados.getAutorizados();
		for (Autorizado au : autorizadoList){
			if(au.getId() == 5L){
				assertEquals(au.getApellidos(), "Apellidos");
			}
		}
	}


	@Test
	@Requisitos("RF8")
	public void testAutorizadoNoEncontrado(){

		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		final Long id = 100000L;
		assertThrows(AutorizadoNoEncontradoException.class, ()-> gestionAutorizados.eliminarAutorizados(usuario1, id));
	}

	@Test
	@Requisitos("RF8")
	public void testEliminarAutorizado(){
		Date date = new Date();
		Autorizado autorizado = new Autorizado(150L, "100L", "Nombre", "Apellidos",
				"Direccion", date, "Estado", date, date);
		List<PersonaJuridica> pjP = new ArrayList<>();
		autorizado.setEmpresas(pjP);
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		try {
			List<Autorizado> autor = new ArrayList<>();
			gestionClientes.darDeAltaCliente(usuario1, "50L", "Juridico", "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais", autor, "Ciudad");
			List<PersonaJuridica> personaJuridicas = gestionClientes.getPersonasJuridicas();
			Long id = null;
			PersonaJuridica personaJuridica1 = null;
			for (PersonaJuridica pj : personaJuridicas){
				if (pj.getIdentificacion().equals("50L")){
					id = pj.getId();
					personaJuridica1 = pj;
				}
			}
			gestionAutorizados.anadirAutorizados(usuario1, personaJuridica1, autorizado);

			gestionAutorizados.eliminarAutorizados(usuario1, autorizado.getId());
			List<Autorizado> autorizadoList = gestionAutorizados.getAutorizados();

			Autorizado autorizado1 = null;
			for (Autorizado au: autorizadoList){
				if (au.getIdentificacion() == "100L"){
					autorizado1 = au;
				}
			}
			List<PersonaJuridica> personaJuridicaList = autorizado1.getEmpresas();
			boolean ok = true;
			for (PersonaJuridica pj : personaJuridicaList){
				if (pj.getAutorizados().contains(autorizado1)){
					ok = false;
				}
			}
			assertEquals("Baja",autorizado1.getEstado());
			assertEquals(0, autorizado1.getEmpresas().size());
			assertEquals(true, ok);




		}catch (NoEsAdministrativo e){
			fail("NoEsAdministrativo (NO DEBERIA)");
		}catch (UsuarioNoEncontrado e){
			fail("UsuarioNoEncontrado (NO DEBERIA)");
		}catch (PersonaJuridicaNoEncontrada e){
			fail("PersonaJuridicaNoEncontrada (NO DEBERIA)");
		}catch (ClienteNoValidoException e){
			fail("ClienteNoValido (NO DEBERIA)");
		}catch (AutorizadoNoEncontradoException e){
			fail("AutorizadoNoEncontrado (NO DEBERIA)");
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
			gestionClientes.darDeAltaCliente(usuario1, "39L", "Fisica", "Razon", "Nombre", "Apellidos", date,
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
					assertEquals("Baja", pl.getEstado());
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
	@Requisitos("RF10")
	public void testAccesoPersonaAutorizada(){
		//este usuario tiene asociado un autorizado en la BBDD
		Usuario esoler = new Usuario("eSoler", "bbdd", true);
		try{
			assertEquals(true, gestionUsuarios.usuarioCorrecto(esoler));
		} catch (UsuarioNoEncontrado usuarioNoEncontrado) {
			fail("Usuario no encontrado");
		} catch (EmpresaNoTieneAcceso empresaNoTieneAcceso) {
			fail("Una empresa no puede tener acceso");
		} catch (AutorizadoSoloTieneAccesoACuentasClienteBloqueado e) {
			fail("Autorizado que solo tiene acceso a las cuentas de clientes que estan bloqueados");
		} catch (PersonaFisicaBloqueada e) {
			fail("Persona fisica bloqueada esta intentando acceder a la aplicacion");
		} catch (AutorizadoBloqueado autorizadoBloqueado) {
			fail("Persona autorizada bloqueada esta intentando acceder a la aplicacion");
		}


	}

	@Test
	@Requisitos("RF11")
	public void testObtenerCuentasHolandaNingunaCoincide(){
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		assertThrows(NingunaCuentaCoincideConLosParametrosDeBusqueda.class, () -> gestionCuentas.getCuentasHolanda(ibai,"Activa" , "3678367287"));
	}

	@Test
	@Requisitos("RF11")
	public void testObtenerCuentasHolanda(){
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		try{
			System.out.println(gestionCuentas.getCuentasHolanda(ibai, "Baja", null));
		} catch (NingunaCuentaCoincideConLosParametrosDeBusqueda e) {
			fail("Los parametros de busqueda no han obtenido ningun resultado");
		} catch (UsuarioNoEncontrado usuarioNoEncontrado) {
			fail("Usuario no encontrado en la BBDD");
		} catch (NoEsAdministrativo noEsAdministrativo) {
			fail("Usuario no es administrativo");
		}
	}

	@Test
	@Requisitos("RF11")
	public void testObtenerClientesHolandaNingunaCoincide(){
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		assertThrows(NingunClienteCoincideConLosParametrosDeBusqueda.class, () -> gestionClientes.getClientesHolanda(ibai, "3982749823759F",  "su casa", "29834", "Mozambique"));
	}

	@Test
	@Requisitos("RF11")
	public void testObtenerClientesHolanda(){
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		try{
			gestionClientes.getClientesHolanda(ibai, "666L", "Alejandro", "Requena", "Direccion");
			System.out.println(gestionClientes.getClientesHolanda(ibai, "666L", "Alejandro", "Requena", "Direccion"));
		} catch (UsuarioNoEncontrado usuarioNoEncontrado) {
			fail("Usuario no encontrado en la BBDD");
		} catch (NoEsAdministrativo noEsAdministrativo) {
			fail("Usuario no es administrativo");
		} catch (NingunClienteCoincideConLosParametrosDeBusqueda ningunClienteCoincideConLosParametrosDeBusqueda) {
			fail("Ningun cliente fue encontrado con estos parametros de busqueda");
		}
	}


	@Test
	@Requisitos("RF13")
	public void testRegistrarTransaccionConCantidadErronea(){
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		Transaccion tx1 = new Transaccion(1L, new Date(), 0.0, "Quiero hackear el sistema", "Ismael", "Transferencia regular");
		Segregada cuentaSegregada1 = new Segregada("ES394583094850", "", new Date(), "Activa", "Segregada", 0.1);
		Segregada cuentaSegregada2 = new Segregada("ES394583094851", "", new Date(), "Activa", "Segregada", 0.1);

		assertThrows(TransaccionConCantidadIncorrecta.class, () -> gestionTransacciones.registrarTransaccionFintech(ibai, cuentaSegregada1, cuentaSegregada2, tx1));
	}

	@Test
	@Requisitos("RF13")
	public void testCuentaNoExistenteRealizaTransaccion(){
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		Transaccion tx1 = new Transaccion(1L, new Date(), 1.0, "Transaccion correcta", "Ismael",  "Transferencia regular");
		Segregada cuentaSegregada1 = new Segregada("ES394583094850", "", new Date(), "Activa", "Segregada", 0.1);
		Segregada cuentaSegregada2 = new Segregada("990", "", new Date(), "Activa", "Segregada", 0.1);

		assertThrows(CuentaNoEncontradaException.class, () -> gestionTransacciones.registrarTransaccionFintech(ibai, cuentaSegregada1, cuentaSegregada2, tx1));
	}

	@Test
	@Requisitos("RF13")
	public void testCuentaDeBajaRealizaTransaccion(){
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		Transaccion tx1 = new Transaccion(1L, new Date(), 1.0, "Transaccion correcta", "Ismael", "Transferencia regular");
		Segregada cuentaDeBaja = new Segregada("ES394583094857", "", new Date(), "Baja", "Segregada", 0.1);
		Segregada cuentaDeBaja2 = new Segregada("ES394583094858", "", new Date(), "Baja", "Segregada", 0.1);
		assertThrows(CuentaDeBajaNoPuedeRegistrarTransaccion.class, () -> gestionTransacciones.registrarTransaccionFintech(ibai, cuentaDeBaja, cuentaDeBaja2, tx1));
	}

	@Test
	@Requisitos("RF13")
	public void testCuentaRealizaTransaccionASiMisma(){
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		Transaccion tx1 = new Transaccion(1L, new Date(), 1.0, "Transaccion correcta", "Ismael",  "Transferencia regular");
		Segregada cuentaSegregada1 = new Segregada("ES394583094850", "", new Date(), "Activa", "Segregada", 0.1);
		assertThrows(MismaCuentaOrigenYDestino.class, () -> gestionTransacciones.registrarTransaccionFintech(ibai, cuentaSegregada1, cuentaSegregada1, tx1));
	}

	@Test
	@Requisitos("RF13")
	public void testSaldoInsuficiente(){
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		Transaccion tx1 = new Transaccion(1L, new Date(), 1.0, "Transaccion correcta", "Ismael",
				"Transferencia regular");
		Divisa euros = new Divisa("EUR", "Euros", '€', 1);
		Segregada saldoCero = new Segregada("ES0", "", new Date(), "Activa", "Segregada", 0.1);
		Segregada destino = new Segregada("ES394583094850", "", new Date(), "Activa", "Segregada", 0.1);
		tx1.setEmisor(euros);
		assertThrows(SaldoInsuficiente.class, () -> gestionTransacciones.registrarTransaccionFintech(ibai, saldoCero, destino, tx1));

	}

	@Test
	@Requisitos("RF13")
	public void testDivisaNoCoincide (){

		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		Transaccion tx1 = new Transaccion(1L, new Date(), 1.0, "Transaccion correcta", "Ismael",
				"Transferencia regular");
		Divisa euros = new Divisa("EUR", "Euros", '€', 1);
		Divisa dolares = new Divisa("USD", "Dolar", '$', 0.95);
		Segregada saldoCero = new Segregada("ES0", "", new Date(), "Activa", "Segregada", 0.1);
		Segregada destino = new Segregada("ES394583094850", "", new Date(), "Activa", "Segregada", 0.1);
		tx1.setEmisor(dolares);
		assertThrows(DivisaNoCoincide.class, () -> gestionTransacciones.registrarTransaccionFintech(ibai, saldoCero, destino, tx1));

	}


	@Test
	@Requisitos("RF13")
	public void testRealizarTransaccion(){

		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		Transaccion tx1 = new Transaccion(1L, new Date(), 1.0, "Transaccion correcta", "Ismael",
				"Transferencia regular");
		Divisa euros = new Divisa("EUR", "Euros", '€', 1);
		Segregada origen  			= new Segregada("ES1", "", new Date(), "Activa", "Segregada", 0.1);
		Segregada destino  			= new Segregada("ES2", "", new Date(), "Activa", "Segregada", 0.1);
		tx1.setReceptor(euros);
		tx1.setEmisor(euros);
		tx1.setCantidad(100.0);

		try{
			gestionTransacciones.registrarTransaccionFintech(ibai, origen, destino, tx1);
			List<Segregada> segregadaList = gestionCuentas.obtenerCuentasSegregada();
			Segregada o = null;
			Segregada d = null;
			for (Segregada s: segregadaList){

				if (s.getIBAN().equals(origen.getIBAN())){
					o = s;
				}
				if (s.getIBAN().equals(destino.getIBAN())){
					d = s;
				}
			}
			Double saldoDestino = 600.0;
			assertEquals(saldoDestino, d.getCr().getSaldo());
			Double saldoOrigen  = 399.0;
			assertEquals(saldoOrigen, o.getCr().getSaldo());
			assertEquals(o, tx1.getOrigen());
			assertEquals(d, tx1.getDestino());


		}catch (UsuarioNoEncontrado usuarioNoEncontrado) {
			usuarioNoEncontrado.printStackTrace();
		} catch (MismaCuentaOrigenYDestino mismaCuentaOrigenYDestino) {
			mismaCuentaOrigenYDestino.printStackTrace();
		} catch (SaldoInsuficiente saldoInsuficiente) {
			saldoInsuficiente.printStackTrace();
		} catch (CuentaNoEncontradaException e) {
			e.printStackTrace();
		} catch (TransaccionConCantidadIncorrecta transaccionConCantidadIncorrecta) {
			transaccionConCantidadIncorrecta.printStackTrace();
		} catch (DivisaNoCoincide divisaNoCoincide) {
			divisaNoCoincide.printStackTrace();
		} catch (CuentaDeBajaNoPuedeRegistrarTransaccion cuentaDeBajaNoPuedeRegistrarTransaccion) {
			cuentaDeBajaNoPuedeRegistrarTransaccion.printStackTrace();
		} catch (NoEsAdministrativo noEsAdministrativo) {
			noEsAdministrativo.printStackTrace();
		}


	}



	@Test
	@Requisitos("RF16")
	public void testBloquearClienteNoExistente(){
		Usuario karim = new Usuario("Karim", "Benzedios", true);
		PersonaFisica personaFisica4 = new PersonaFisica(69L, "800L", "Fisica", "Activo", new Date(), null, "Direccion",
				"Ciudad", 2967, "Pais", "Alex", "Requena", new Date());
		assertThrows(ClienteNoEncontradoException.class, () -> gestionClientes.bloquearCliente(karim, personaFisica4));
	}

	@Test
	@Requisitos("RF16")
	public void testBloquearPersonaFisicaYJuridica(){
		Usuario karim = new Usuario("Karim", "Benzedios", true);
		PersonaFisica personaFisica4 = new PersonaFisica(8L, "800L", "Fisica", "Activo", new Date(), null, "Direccion",
				"Ciudad", 2967, "Pais", "Alex", "Requena", new Date());
		PersonaJuridica personaJuridica4 = new PersonaJuridica(9L, "2001L", "Juridico", "Activo", new Date(), null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		try{
			gestionClientes.bloquearCliente(karim, personaFisica4);
			gestionClientes.bloquearCliente(karim, personaJuridica4);
		} catch (UsuarioNoEncontrado usuarioNoEncontrado) {
			fail("Usuario no encontrado en la BBDD");
		} catch (TipoNoValidoException e) {
			fail("El tipo del cliente no es valido");
		} catch (ClienteNoEncontradoException e) {
			fail("El cliente no fue encontrado en la BBDD");
		} catch (NoEsAdministrativo noEsAdministrativo) {
			fail("El usuario que intenta bloquear al cliente no es administrativo");
		} catch (BloquearClienteYaBloqueado bloquearClienteYaBloqueado) {
			fail("El usuario ha intentado bloquear a un cliente que ya estaba bloqueado");
		}
		for(PersonaFisica pf : gestionClientes.getPersonasFisicas()){
			if(pf.getId().equals(6L)){
				assertEquals("Bloqueado", pf.getEstado());
			}
		}
		for(PersonaJuridica pj : gestionClientes.getPersonasJuridicas()){
			if(pj.getId().equals(7L)){
				assertEquals("Bloqueado", pj.getEstado());
			}
		}
	}

	@Test
	@Requisitos("RF16")
	public void testDesbloquearPersonaFisicaYJuridica(){
		Usuario karim = new Usuario("Karim", "Benzedios", true);
		PersonaFisica personaFisica4 = new PersonaFisica(8L, "800L", "Fisica", "Activo", new Date(), null, "Direccion",
				"Ciudad", 2967, "Pais", "Alex", "Requena", new Date());
		PersonaJuridica personaJuridica4 = new PersonaJuridica(9L, "2001L", "Juridico", "Activo", new Date(), null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		try{
			//bloqueamos
			gestionClientes.bloquearCliente(karim, personaFisica4);
			gestionClientes.bloquearCliente(karim, personaJuridica4);
			//desbloqueamos
			gestionClientes.desbloquearCliente(karim, personaFisica4);
			gestionClientes.desbloquearCliente(karim, personaJuridica4);
		} catch (UsuarioNoEncontrado usuarioNoEncontrado) {
			fail("Usuario no encontrado en la BBDD");
		} catch (TipoNoValidoException e) {
			fail("El tipo del cliente no es valido");
		} catch (ClienteNoEncontradoException e) {
			fail("El cliente no fue encontrado en la BBDD");
		} catch (NoEsAdministrativo noEsAdministrativo) {
			fail("El usuario que intenta bloquear al cliente no es administrativo");
		} catch (DesbloquearClienteQueNoEstaBloqueado desbloquearClienteQueNoEstaBloqueado) {
			fail("El usuario ha intentado desbloquear a un cliente que no estaba bloqueado");
		} catch (BloquearClienteYaBloqueado bloquearClienteYaBloqueado) {
			fail("El usuario ha intentado bloquear a un cliente que ya estaba bloqueado");
		}
		for(PersonaFisica pf : gestionClientes.getPersonasFisicas()){
			if(pf.getId().equals(6L)){
				assertEquals("Activo", pf.getEstado());
			}
		}
		for(PersonaJuridica pj : gestionClientes.getPersonasJuridicas()){
			if(pj.getId().equals(7L)){
				assertEquals("Activo", pj.getEstado());
			}
		}
	}

	@Test
	@Requisitos("RF16")
	public void testBloquearClienteYaBloqueado(){
		Usuario karim = new Usuario("Karim", "Benzedios", true);
		PersonaFisica personaFisica4 = new PersonaFisica(8L, "800L", "Fisica", "Activo", new Date(), null, "Direccion",
				"Ciudad", 2967, "Pais", "Alex", "Requena", new Date());
		PersonaJuridica personaJuridica4 = new PersonaJuridica(9L, "2001L", "Juridico", "Activo", new Date(), null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		try{
			//bloqueamos
			gestionClientes.bloquearCliente(karim, personaFisica4);
			gestionClientes.bloquearCliente(karim, personaJuridica4);
		} catch (UsuarioNoEncontrado usuarioNoEncontrado) {
			fail("Usuario no encontrado en la BBDD");
		} catch (TipoNoValidoException e) {
			fail("El tipo del cliente no es valido");
		} catch (ClienteNoEncontradoException e) {
			fail("El cliente no fue encontrado en la BBDD");
		} catch (NoEsAdministrativo noEsAdministrativo) {
			fail("El usuario que intenta bloquear al cliente no es administrativo");
		} catch (BloquearClienteYaBloqueado bloquearClienteYaBloqueado) {
			fail("El usuario ha intentado bloquear a un cliente que ya estaba bloqueado");
		}
		assertThrows(BloquearClienteYaBloqueado.class, () -> gestionClientes.bloquearCliente(karim, personaFisica4));
		assertThrows(BloquearClienteYaBloqueado.class, () -> gestionClientes.bloquearCliente(karim, personaJuridica4));
	}
	@Test
	@Requisitos("RF16")
	public void testDesbloquearClienteNoBloqueado(){
		Usuario karim = new Usuario("Karim", "Benzedios", true);
		PersonaFisica personaFisica4 = new PersonaFisica(8L, "800L", "Fisica", "Activo", new Date(), null, "Direccion",
				"Ciudad", 2967, "Pais", "Alex", "Requena", new Date());
		PersonaJuridica personaJuridica4 = new PersonaJuridica(9L, "2001L", "Juridico", "Activo", new Date(), null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");

		assertThrows(DesbloquearClienteQueNoEstaBloqueado.class, () -> gestionClientes.desbloquearCliente(karim, personaFisica4));
		assertThrows(DesbloquearClienteQueNoEstaBloqueado.class, () -> gestionClientes.desbloquearCliente(karim, personaJuridica4));
	}

	@Test
	@Requisitos("RF16")
	public void testAccesoAutorizadoQueSoloTieneAccesoACuentasDeClientesBloqueados(){
		Usuario karim = new Usuario("Karim", "Benzedios", true);
		assertThrows(AutorizadoSoloTieneAccesoACuentasClienteBloqueado.class, () -> gestionUsuarios.usuarioCorrecto(karim));
	}

	@Test
	@Requisitos("RF16")
	public void testAccesoPersonaFisicaBloqueada(){
		Usuario turing = new Usuario("alan", "turing", false);
		assertThrows(PersonaFisicaBloqueada.class, () -> gestionUsuarios.usuarioCorrecto(turing));
	}

}
