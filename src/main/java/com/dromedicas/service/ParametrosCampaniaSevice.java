package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Paremetroscampania;
import com.dromedicas.eis.ParametrosCampaniaDao;

@Stateless
public class ParametrosCampaniaSevice {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private ParametrosCampaniaDao dao;

	
	public List<Paremetroscampania> findAllParemetroscampanias() {
		// TODO Auto-generated method stub
		return dao.findAllParemetroscampanias();
	}

	
	public Paremetroscampania obtenerParemetroscampaniaById(Paremetroscampania instance) {
		// TODO Auto-generated method stub
		return dao.obtenerParemetroscampaniaById(instance);
	}

	
	public void insertParemetroscampania(Paremetroscampania instance) {
		dao.insertParemetroscampania(instance);
		
	}

	
	public void updateParemetroscampania(Paremetroscampania instance) {
		System.out.println("CAMPANA RECIBIDA:  " +  instance.getCampania().getNombrecampania()  );
		
		dao.updateParemetroscampania(instance);
		
	}

	
	public void deleteParemetroscampania(Paremetroscampania instance) {
		dao.deleteParemetroscampania(instance);
		
	}

}
