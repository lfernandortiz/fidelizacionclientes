package com.dromedicas.service;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Parametrosemail;
import com.dromedicas.eis.ParametrosemailDao;

public class ParametrosEmialService {
	
	@EJB
	private ParametrosemailDao dao;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	public Parametrosemail obtenerParametrosemail() {
		Query query = em.createQuery("select p FROM Parametrosemail p");
		Parametrosemail temp = null;		
		try {
			temp = (Parametrosemail) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No encontrado");			
		}			
		return temp;
	}

	
	public void insertParametrosemail(Parametrosemail instance) {
		dao.insertParametrosemail(instance);

	}

	
	public void updateParametrosemail(Parametrosemail instance) {
		dao.updateParametrosemail(instance);

	}

	
	public void deleteParametrosemail(Parametrosemail instance) {
		dao.deleteParametrosemail(instance);

	}

}
