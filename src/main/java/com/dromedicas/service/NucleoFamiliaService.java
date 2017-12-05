package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Estudioafiliado;
import com.dromedicas.domain.Nucleofamilia;
import com.dromedicas.eis.EstudioAfiliadoDao;
import com.dromedicas.eis.NucleoFamiliaDao;

@Stateless
public class NucleoFamiliaService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;	
	
	@EJB
	private NucleoFamiliaDao dao;

	public List<Nucleofamilia> findAllNucleofamilias(){
		return dao.findAllNucleofamilias();
	}
	
	public Nucleofamilia obtenerNucleofamiliaById(Nucleofamilia instance){
		return dao.obtenerNucleofamiliaById(instance);		
	}
	
	public void insertNucleofamilia(Nucleofamilia instance){
		dao.insertNucleofamilia(instance);
	}
	
	public void updateNucleofamilia(Nucleofamilia instance){
		dao.updateNucleofamilia(instance);
	}
	
	public void deleteNucleofamilia(Nucleofamilia instance){
		dao.deleteNucleofamilia(instance);
	}	
}
