package com.dromedicas.test;

import java.security.Key;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class Test {
	
	static EntityManager em = null;
	static EntityTransaction tx = null;
	static EntityManagerFactory emf = null;
	
	
	public static void main(String[] args) {

		try {
			long t1 = System.currentTimeMillis();
			
//			Key k = MacProvider.generateKey();
			String k = "CSK395";
			String jwt = Jwts.builder()
			 .setSubject("PuntosFarmanorte")
			 .signWith(SignatureAlgorithm.HS256, k)
			 .setIssuedAt(new Date(t1))
			 .setExpiration(new Date(t1 + 900000))
			 .claim("email", "cliente@yahoo.com")
			 .compact();
		
			System.out.println("K: " + k.toString());
			
//			JsonObject json ;
//			json = Json.createObjectBuilder().add("JWT", compactJws).build();
//			
			
			System.out.println("JWT:  " + jwt );
			
//			Logger log = Logger.getLogger("TestPuntosFarmanorte");
//
//			log.debug("Preparando contexto de persistencia");
//			
//			emf = Persistence.createEntityManagerFactory("PuntosFPU");
//			
//			em = emf.createEntityManager();
//
//			log.debug("Iniciando test Persona Entity con JPA");
//			@SuppressWarnings("unused")
//			EntityTransaction tx = em.getTransaction();
//
////			tx.begin();
//
////			Empresa emp = new Empresa();
////			emp.setNit("900265730-0");
////			emp.setNombreEmpresa("Dromedicas del Oriente SAS");
////			emp.setDireccion("Avenida 7A # 0BN - 36 Sevilla");
////			emp.setTelefono("5781240");
////			emp.setEmailNotificaciones("info@dromedicas.com.co");
//			
//			
//			@SuppressWarnings("unchecked")
//			List<Empresa> empresaList = em.createNamedQuery("Empresa.findAll").getResultList();
//			
//			for(Empresa e: empresaList )
//				System.out.println(e);
//
////			log.debug("Objeto a persistir: " + emp);
////
////			em.persist(emp);
////			
//	//		tx.commit();
////
////			log.debug("Objeto persistido: " + emp);
//
//			log.debug("Fin test Persona Entity con JPA");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
