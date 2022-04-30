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

		PersonaJuridica pj = new PersonaJuridica(null, "21L", "Juridico", "Activo", date, null, "Direccion",
				"sevilla", 29002, "pais", "ninguna");
		em.persist(pj);

		Usuario usuario1 = new Usuario("AlexEkken", "1234", true);
		Usuario usuario2 = new Usuario("Chikano", "Mascarilla", false);
		PersonaJuridica personaJuridica1 = new PersonaJuridica(null, "55L", "Juridico", "Activo", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		usuario1.setCliente(personaJuridica1);
		em.persist(personaJuridica1);
		em.persist(usuario1);
		em.persist(usuario2);

		Segregada segregada2 = new Segregada("ES2057883234722030876293", "", date, "Baja", "Segregada", 0.0);
		em.persist(segregada2);
		PersonaFisica personaFisica1 = new PersonaFisica(null, "300L", "Fisica", "Activo", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Alex", "Requena", date);
		em.persist(personaFisica1);

		PersonaJuridica personaJuridica2 = new PersonaJuridica(null, "108L", "Juridico", "Activo", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		Segregada segregada3 = new Segregada("ES20578832365722030876293", "", date, "Activa", "Segregada", 0.0);
		segregada3.setCliente(personaJuridica2);
		List<CuentaFintech> listaCuentas = new ArrayList<>();
		listaCuentas.add(segregada3);
		listaCuentas.add(segregada2);
		em.persist(segregada3);
		personaJuridica2.setCuentasFintech(listaCuentas);
		em.persist(personaJuridica2);


		Usuario usuario3= new Usuario("Cristiano", "Ronaldo", false);
		personaJuridica2.setUsuario(usuario3);
		Autorizado autorizado1 = new Autorizado(null, "36L", "Cristiano", "Ronaldo", "Cielo", new Date(), "Activo", new Date(), null);
		usuario3.setAutorizado(autorizado1);
		List<Autorizado> listaAutorizados = new ArrayList<>();
		listaAutorizados.add(autorizado1);
		personaJuridica2.setAutorizados(listaAutorizados);
		em.persist(autorizado1);
		em.persist(usuario3);


		//RF16
		Usuario karim = new Usuario("Karim", "Benzedios", true);
		PersonaFisica personaFisica4 = new PersonaFisica(null, "800L", "Fisica", "Activo", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Alex", "Requena", date);
		PersonaJuridica personaJuridica4 = new PersonaJuridica(null, "2001L", "Juridico", "Activo", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		PersonaJuridica personaJuridicaBloqueada = new PersonaJuridica(null, "9999L", "Juridico", "Bloqueado", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Sociedad Anonima");
		Segregada segregada6 = new Segregada("ES2057883255722030876293", "", date, "Activa", "Segregada", 0.0);
		//asociamos alguna cuenta al cliente bloqueado
		List<CuentaFintech> listaCuentasFintech = new ArrayList<>();
		listaCuentasFintech.add(segregada6);
		personaJuridicaBloqueada.setCuentasFintech(listaCuentasFintech);
		//añadimos las empresas al autorizado
		List<PersonaJuridica> listaEmpresas = new ArrayList<>();
		listaEmpresas.add(personaJuridicaBloqueada);
		Autorizado autorizado4 = new Autorizado(null, "2022L", "autorizado", "4", "Cielo", new Date(), "Activo", new Date(), null);
		List<Autorizado> listaAutorizados2 = new ArrayList<>();
		listaAutorizados.add(autorizado4);
		autorizado4.setEmpresas(listaEmpresas);
		personaJuridicaBloqueada.setAutorizados(listaAutorizados);
		//vinculamos al usuario karim con el autorizado que esta vinculado al cliente bloqueado
		autorizado4.setUsuario(karim);
		karim.setAutorizado(autorizado4);
		karim.setCliente(personaFisica4);
		em.persist(segregada6);
		em.persist(personaJuridicaBloqueada);
		em.persist(autorizado4);
		em.persist(personaFisica4);
		//System.out.println(personaFisica4.getId());
		em.persist(personaJuridica4);
		//System.out.println(personaJuridica4.getId());
		em.persist(karim);

		Usuario turing = new Usuario("alan", "turing", false);
		PersonaFisica personaFisica7 = new PersonaFisica(null, "666L", "Fisica", "Bloqueado", date, null, "Direccion",
				"Ciudad", 2967, "Pais", "Alejandro", "Requena", date);
		turing.setCliente(personaFisica7);
		personaFisica7.setUsuario(turing);
		em.persist(personaFisica7);
		em.persist(turing);

		//RF13
		Usuario ibai = new Usuario("Ibai", "Llanos", true);
		Segregada cuentaSegregada1 = new Segregada("ES394583094850", "", new Date(), "Activa", "Segregada", 0.1);
		Segregada cuentaSegregada2 = new Segregada("ES394583094851", "", new Date(), "Activa", "Segregada", 0.1);
		Segregada cuentaDeBaja = new Segregada("ES394583094857", "", new Date(), "Baja", "Segregada", 0.1);
		Segregada cuentaDeBaja2 = new Segregada("ES394583094858", "", new Date(), "Baja", "Segregada", 0.1);

		Segregada saldoCero = new Segregada("ES0", "", new Date(), "Activa", "Segregada", 0.1);
		CuentaReferencia crCero = new CuentaReferencia("Cr0", "", "Santander", "Sucursal", "España",
				0.0, new Date(), true );
		Divisa euros   = new Divisa("EUR", "Euros", '€', 1);
		Divisa dolares = new Divisa("USD", "Dolar", '$', 0.95);

		em.persist(dolares);
		em.persist(euros);
		crCero.setSegregada(saldoCero);
		crCero.setDivisa(euros);
		saldoCero.setCr(crCero);
		em.persist(crCero);
		em.persist(saldoCero);

		em.persist(ibai);
		em.persist(cuentaSegregada1);
		em.persist(cuentaSegregada2);
		em.persist(cuentaDeBaja);
		em.persist(cuentaDeBaja2);

		//RF13 testTOCHO TEST DE JHONNY TEST

		Segregada origen  			= new Segregada("ES1", "", new Date(), "Activa", "Segregada", 0.1);
		CuentaReferencia crOrigen 	= new CuentaReferencia("ESCrOrigen", "", "Ebury", "Uma",
				"España", 500.00, new Date(), true);
		origen.setCr(crOrigen);
		crOrigen.setSegregada(origen);
		crOrigen.setDivisa(euros);
		em.persist(crOrigen);
		em.persist(origen);

		Segregada destino  			= new Segregada("ES2", "", new Date(), "Activa", "Segregada", 0.1);
		CuentaReferencia crDestino 	= new CuentaReferencia("ESCrDestino", "", "Ebury", "Uma",
				"España", 500.00, new Date(), true);
		destino.setCr(crDestino);
		crDestino.setSegregada(destino);
		crDestino.setDivisa(euros);
		em.persist(crDestino);
		em.persist(destino);








		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
