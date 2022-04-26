package turingFintech;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import clases.ejb.GestionCuentas;
import clases.ejb.GestionClientes;
import java.util.logging.Logger;

import javax.naming.NamingException;

import clases.ejb.exceptions.TipoNoValidoException;
import clases.ejb.exceptions.TuringTestException;
import es.uma.informatica.sii.anotaciones.Requisitos;
import org.junit.Before;
import org.junit.Test;

public class TuringFintech {
	
	private static final Logger LOG = Logger.getLogger(TuringFintech.class.getCanonicalName());

	private static final String CLIENTES_EJB = "java:global/classes/ClientesEJB";
	private static final String CUENTAS_EJB = "java:global/classes/CuentasEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "turingFintechTest";
	
	private GestionClientes gestionClientes;
	private GestionCuentas gestionCuentas;


	@Before
	public void setup() throws NamingException  {
		gestionClientes = (GestionClientes) SuiteTest.ctx.lookup(CLIENTES_EJB);
		gestionCuentas = (GestionCuentas) SuiteTest.ctx.lookup(CUENTAS_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
	public void testtest(){

	}

	@Test
	@Requisitos("RF2")
	public void testDardeAltaCuentaTipoNoValidoException(){

		try{
			final String tipo = "poooled";
			gestionCuentas.aperturaCuenta("","", tipo);
			fail ("Debe lanzar una excepcion");

		}catch (TipoNoValidoException e){
			// OK
		}catch (TuringTestException e){
			fail ("Debería haber lanzado una excepcion");
		}
	}

	@Test
	@Requisitos("RF2")
	public void testDardeAltaCuenta(){
		final String tipo = "Segregada";
		try{

			gestionCuentas.aperturaCuenta("","", tipo);


		}catch (TipoNoValidoException e){
			fail("Lanzo una excepcion al insertar");
		}
	}
		try{
			gestionCuentas.

		}


}
