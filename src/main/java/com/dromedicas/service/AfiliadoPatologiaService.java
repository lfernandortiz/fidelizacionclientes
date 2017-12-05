package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Afiliadopatologia;
import com.dromedicas.eis.AfiliadoPatologiaDao;

@Stateless
public class AfiliadoPatologiaService implements Serializable {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private AfiliadoPatologiaDao dao;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Afiliadopatologia> findAllAfiliadopatologias(){
		return dao.findAllAfiliadopatologias();
	}
	
	public Afiliadopatologia obtenerAfiliadopatologiaById(Afiliadopatologia instance){
		return dao.obtenerAfiliadopatologiaById(instance);
	}
	
	public void insertAfiliadopatologia(Afiliadopatologia instance){
		dao.insertAfiliadopatologia(instance);
	}
	
	public void updateAfiliadopatologia(Afiliadopatologia instance){
		dao.updateAfiliadopatologia(instance);
	}
	
	public void deleteAfiliadopatologia(Afiliadopatologia instance){
		dao.deleteAfiliadopatologia(instance);
	}

}
