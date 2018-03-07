package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Patologiacampania;
import com.dromedicas.eis.PatologiaCampaniaDao;


@Stateless
public class PatologiaCampaniaSevice {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private PatologiaCampaniaDao dao;


	@SuppressWarnings("unchecked")
	public List<Patologiacampania> findAllPatologiacampanias() {
		// TODO Auto-generated method stub
		return dao.findAllPatologiacampanias();
	}

	
	public Patologiacampania obtenerPatologiacampaniaById(Patologiacampania instance) {
		// TODO Auto-generated method stub
		return dao.obtenerPatologiacampaniaById(instance);
	}

	
	public void insertPatologiacampania(Patologiacampania instance) {
		dao.insertPatologiacampania(instance);

	}

	
	public void updatePatologiacampania(Patologiacampania instance) {
		dao.updatePatologiacampania(instance);

	}

	
	public void deletePatologiacampania(Patologiacampania instance) {
		dao.deletePatologiacampania(instance);
	}
	

}
