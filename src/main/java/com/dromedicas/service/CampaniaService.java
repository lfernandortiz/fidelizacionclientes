package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Campania;
import com.dromedicas.eis.CampaniaDao;

@Stateless
public class CampaniaService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private CampaniaDao dao;
		
	public List<Campania> findAllCampanias() {
		return dao.findAllCampanias();
	}

	
	public Campania obtenerCampaniaById(Campania instance) {
		return dao.obtenerCampaniaById(instance);
	}
	
	public Campania obtenerCampaniaById(Integer id) {		
		Query query = em.createQuery("FROM Campania c WHERE c.idcampania = :id ");
		query.setParameter("id", id);
		Campania temp = null;	
		try { 
			temp = (Campania) query.getSingleResult();
			
		} catch (NoResultException e) {
			System.out.println("Campanaia no encontrado");			
		}		
		return temp;
	}
	
	
	public void insertCampania(Campania instance) {
		dao.insertCampania(instance);

	}

	
	public Integer updateCampania(Campania instance) {
		return dao.updateCampania(instance);

	}

	
	public void deleteCampania(Campania instance) {
		dao.deleteCampania(instance);
	}
	

}
