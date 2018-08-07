package com.dromedicas.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Parametrosemail;
import com.dromedicas.eis.ParametrosemailDao;

@Stateless
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
		Parametrosemail temp =  this.obtenerParametrosemailPorFinalidad("1");
		if(temp == null){
			dao.updateParametrosemail(temp);
		}else{
			temp = instance;
			dao.updateParametrosemail(temp);	
		}
	}

	
	public void deleteParametrosemail(Parametrosemail instance) {
		dao.deleteParametrosemail(instance);

	}
	
	/**
	 * Obtiene parametros de correo segun la finalidad del correo
	 * @param fin
	 * @return
	 */
	public Parametrosemail obtenerParametrosemailPorFinalidad(String fin) {		
		System.out.println("PROPOSITO:  " +  fin);
		Query query = em.createQuery(" from Parametrosemail p where p.proposito = :fin" );
		query.setParameter("fin", fin);
		Parametrosemail temp = null;		
		try {
			temp = (Parametrosemail) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Cuenta de email no encontrada.");			
		}			
		return temp;
	}

}
