package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Tipomiembro;
import com.dromedicas.eis.TipoMiembroDao;

@Stateless
public class TipoMiembroService {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private TipoMiembroDao dao;
	
	public List<Tipomiembro> findAllTipomiembros(){
		return dao.findAllTipomiembros();
	}
	
	public Tipomiembro obtenerTipomiembroById(Tipomiembro instance){
		return dao.obtenerTipomiembroById(instance);
	}
	
	public void insertTipomiembro(Tipomiembro instance){
		dao.insertTipomiembro(instance);
	}
	
	public void updateTipomiembro(Tipomiembro instance){
		dao.updateTipomiembro(instance);
	}
	
	public void deleteTipomiembro(Tipomiembro instance){
		dao.deleteTipomiembro(instance);
	}


}
