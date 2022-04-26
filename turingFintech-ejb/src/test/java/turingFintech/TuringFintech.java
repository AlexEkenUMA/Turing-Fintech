package turingFintech;

import clases.ejb.GestionCuentas;
import clases.ejb.GestionClientes;

import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import clases.ejb.GestionUsuarios;
import clases.ejb.UsuariosEJB;
import clases.ejb.exceptions.NoEsAdministrativo;
import clases.ejb.exceptions.TipoNoValidoException;
import clases.ejb.exceptions.TuringTestException;
import clases.ejb.exceptions.UsuarioNoEncontrado;
import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.turingFintech.PooledAccount;
import es.uma.turingFintech.Usuario;
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
	@Requisitos("RF5")
	public void testDardeAltaCuentaTipoNoValidoException(){
		//try{
			final String tipo = "poooled";

			Class<TipoNoValidoException> expectedException = TipoNoValidoException.class;
			assertThrows(TipoNoValidoException.class, () -> gestionCuentas.aperturaCuenta("", "",tipo));
			/*
			gestionCuentas.aperturaCuenta("","", tipo);
			fail ("Debe lanzar una excepcion");

		}catch (TipoNoValidoException e){
			// OK
		}catch (TuringTestException e){
			fail ("Debería haber lanzado una excepcion");
		}

			 */
	}

	/*
	@Test
	@Requisitos("RF5")
	public void testDardeAltaCuenta(){
		final String tipo = "Pooled";
		try{

			gestionCuentas.aperturaCuenta("1234","5678", tipo);
			List<PooledAccount> pooled = gestionCuentas.obtenerCuentasPooled();
			assertEquals(1, pooled.size());
			assertEquals("1234", pooled.get(0).getIBAN());
			assertEquals("5678", pooled.get(0).getSWIFT());

		}catch (TipoNoValidoException e){
			fail("Lanzo una excepcion al insertar");
		}
	}

	 */
}
