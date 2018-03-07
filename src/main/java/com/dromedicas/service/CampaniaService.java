package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	
	public void insertCampania(Campania instance) {
		dao.insertCampania(instance);

	}

	
	public void updateCampania(Campania instance) {
		dao.updateCampania(instance);

	}

	
	public void deleteCampania(Campania instance) {
		dao.deleteCampania(instance);
	}
	

}
