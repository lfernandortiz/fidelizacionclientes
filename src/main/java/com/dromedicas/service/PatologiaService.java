package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Patologia;
import com.dromedicas.eis.EstudioAfiliadoDao;
import com.dromedicas.eis.PatologiaDao;

@SuppressWarnings("serial")
@Stateless 
public class PatologiaService  implements Serializable{
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private PatologiaDao dao;
	
	public List<Patologia> findAllPatologias(){
		return dao.findAllPatologias();
	}
	
	public Patologia obtenerPatologiaById(Patologia instance){
		return dao.obtenerPatologiaById(instance);
	}
	
	public void insertPatologia(Patologia instance){
		dao.insertPatologia(instance);
	}
	
	public void updatePatologia(Patologia instance){
		dao.updatePatologia(instance);
	}
	
	public void deletePatologia(Patologia instance){
		dao.deletePatologia(instance);
	}

}
