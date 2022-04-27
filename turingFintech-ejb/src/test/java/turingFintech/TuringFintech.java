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
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		Usuario usuario2 = new Usuario("Chikano", "Mascarilla", false);
		try{
			gestionUsuarios.usuarioCorrecto(usuario1);
			gestionUsuarios.usuarioCorrecto(usuario2);

		}catch (UsuarioNoEncontrado e){
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
		assertThrows(ClienteNoValidoException.class, () -> gestionClientes.darAlta2(32L, tipo, "Razon", "Nombre", "Apellidos", date,
				"Direccion", 2967, "Pais",au, "Ciudad" ));

	}

	@Test
	@Requisitos("RF2")
	public void darDeAltaClienteFisico(){

		final String tipo = "Fisica";
		Date date = new Date();
		List<Autorizado> au = new ArrayList<>();

		try{
			gestionClientes.darAlta2(30L, tipo, "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){
			fail("ClienteNoValido (NO DEBERIA");
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
		try{
			gestionClientes.darAlta2(31L, tipo, "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){
			fail("ClienteNoValido (NO DEBERIA");
		}
		List<PersonaJuridica> personaJuridicas = gestionClientes.getPersonasJuridicas();
		assertEquals(1, personaJuridicas.size());
	}





	@Test
	@Requisitos("RF5")
	public void testDardeAltaCuentaTipoNoValidoException(){

		final String tipo = "poooled";
		Date date = new Date();
		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		Cliente cliente1 = new Cliente(null, 32L, "PersonaFisica", "Activo", date, null,
				"Direccion","Ciudad", 29649,
				"Pais");
		List<DepositadaEn> dpList = new ArrayList<>();
		assertThrows(TipoNoValidoException.class, () -> gestionCuentas.aperturaCuenta(usuario1, gestionClientes.getCliente(37028939023L),
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
			gestionClientes.darAlta2(33L, "Fisica", "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){

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
			gestionClientes.darAlta2(34L, "Fisica", "Razon", "Nombre", "Apellidos", date,
					"Direccion", 2967, "Pais",au, "Ciudad" );
		}catch (ClienteNoValidoException e){

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

}
