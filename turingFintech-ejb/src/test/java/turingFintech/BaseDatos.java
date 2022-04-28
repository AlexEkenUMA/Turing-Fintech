package turingFintech;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.turingFintech.*;
import org.eclipse.persistence.internal.descriptors.PersistenceObject;

public class BaseDatos {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();

		//Cuenta cuenta1 = new Cuenta("ES2114650100722030876293", "");
		//Cuenta cuenta2 = new Cuenta("ES2057883234722030876293", "");
		//Cuenta cuenta3 = new Cuenta("ES4696789300722030876293", "");

		//em.persist(cuenta1);
		//em.persist(cuenta2);
		//em.persist(cuenta3);

		Date date = new Date();
		//CuentaFintech fintech1 = new CuentaFintech("ES2114650100722030876293", "", date
				//, false, "Pooled", 0.0 );

		//CuentaFintech fintech2 = new CuentaFintech("ES2057883234722030876293", "", date
			//	, false, "Segregada", 0.0 );

		//CuentaFintech fintech3 = new CuentaFintech("ES4696789300722030876293", "", date
		//		, false, "", 0.0 );

		//em.persist(fintech1);
		//em.persist(fintech2);
		//em.persist(fintech3);

		//PooledAccount pooledAccount1 = new PooledAccount("ES2114650100722030876293", "", date
		//		, false, "Pooled", 0.0);

		//em.persist(pooledAccount1);



		//em.persist(segregada2);

		//em.persist(cliente1);
		//em.persist(cliente2);
		//em.persist(cliente3);

		PersonaJuridica pj = new PersonaJuridica(null, 21L, "Juridico", "Activo", date, null, "Direccion",
				"sevilla", 29002, "pais", "ninguna");
		em.persist(pj);

		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		Usuario usuario2 = new Usuario("Chikano", "Mascarilla", false);
		PersonaJuridica personaJuridica1 = new PersonaJuridica(null, 55L, "Juridico", "Activo", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		usuario1.setCliente(personaJuridica1);
		em.persist(personaJuridica1);
		em.persist(usuario1);
		em.persist(usuario2);

		Segregada segregada2 = new Segregada("ES2057883234722030876293", "", date, false, "Segregada", 0.0);
		em.persist(segregada2);
		PersonaFisica personaFisica1 = new PersonaFisica(null, 300L, "Fisica", "Activo", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Alex", "Requena", date);
		em.persist(personaFisica1);

		PersonaJuridica personaJuridica2 = new PersonaJuridica(null, 108L, "Juridico", "Activo", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		Segregada segregada3 = new Segregada("ES20578832365722030876293", "", date, true, "Segregada", 0.0);
		segregada3.setCliente(personaJuridica2);
		List<CuentaFintech> listaCuentas = new ArrayList<>();
		listaCuentas.add(segregada3);
		listaCuentas.add(segregada2);
		em.persist(segregada3);
		personaJuridica2.setCuentasFintech(listaCuentas);
		em.persist(personaJuridica2);



		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
