package com.dromedicas.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.dromedicas.domain.Empresa;

public class Test {
	
	static EntityManager em = null;
	static EntityTransaction tx = null;
	static EntityManagerFactory emf = null;
	
	
	public static void main(String[] args) {

		try {
			Logger log = Logger.getLogger("TestPuntosFarmanorte");

			log.debug("Preparando contexto de persistencia");
			
			emf = Persistence.createEntityManagerFactory("PuntosFPU");
			
			em = emf.createEntityManager();

			log.debug("Iniciando test Persona Entity con JPA");
			@SuppressWarnings("unused")
			EntityTransaction tx = em.getTransaction();

//			tx.begin();

//			Empresa emp = new Empresa();
//			emp.setNit("900265730-0");
//			emp.setNombreEmpresa("Dromedicas del Oriente SAS");
//			emp.setDireccion("Avenida 7A # 0BN - 36 Sevilla");
//			emp.setTelefono("5781240");
//			emp.setEmailNotificaciones("info@dromedicas.com.co");
			
			
			@SuppressWarnings("unchecked")
			List<Empresa> empresaList = em.createNamedQuery("Empresa.findAll").getResultList();
			
			for(Empresa e: empresaList )
				System.out.println(e);

//			log.debug("Objeto a persistir: " + emp);
//
//			em.persist(emp);
//			
	//		tx.commit();
//
//			log.debug("Objeto persistido: " + emp);

			log.debug("Fin test Persona Entity con JPA");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
