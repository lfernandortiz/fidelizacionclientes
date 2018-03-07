package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Smscampania;
import com.dromedicas.eis.SmscampaniaDao;

@Stateless
public class SmsCampaniaService {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;
	
	@EJB
	private SmscampaniaDao dao;
	
	
	public List<Smscampania> findAllSmscampanias() {
		return dao.findAllSmscampanias();
	}

	
	public Smscampania obtenerSmscampaniaById(Smscampania instance) {
		return dao.obtenerSmscampaniaById(instance);
	}

	
	public void insertSmscampania(Smscampania instance) {
		dao.insertSmscampania(instance);
	}

	
	public void updateSmscampania(Smscampania instance) {
		dao.updateSmscampania(instance);
	}

	
	public void deleteSmscampania(Smscampania instance) {
		dao.deleteSmscampania(instance);
	}


}
