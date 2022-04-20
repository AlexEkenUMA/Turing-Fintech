package turingFintech;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

public class turingTest {
	
	private static final Logger LOG = Logger.getLogger(turingTest.class.getCanonicalName());

	//private static final String PRODUCTOS_EJB = "java:global/classes/ProductosEJB";
	//private static final String LOTES_EJB = "java:global/classes/LotesEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "turingFintechTest";
	
	//TODO


	@Before
	public void setup() throws NamingException  {

		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
	public void testtest(){

	}

}
