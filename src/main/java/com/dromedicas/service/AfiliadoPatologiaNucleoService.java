package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Afiliadopatologianucleo;
import com.dromedicas.eis.AfiliadoPatologiaNucleoDao;

@SuppressWarnings("serial")
@Stateless
public class AfiliadoPatologiaNucleoService implements Serializable {

	@EJB	
	private AfiliadoPatologiaNucleoDao dao;
	
	public List<Afiliadopatologianucleo> findAllAfiliadopatologianucleos(){
		return dao.findAllAfiliadopatologianucleos();
	}
	
	public Afiliadopatologianucleo obtenerAfiliadopatologianucleoById(Afiliadopatologianucleo instance){
		return dao.obtenerAfiliadopatologianucleoById(instance);
	}
	
	public void insertAfiliadopatologianucleo(Afiliadopatologianucleo instance){
		dao.insertAfiliadopatologianucleo(instance);
	}
	
	public void updateAfiliadopatologianucleo(Afiliadopatologianucleo instance){
		dao.updateAfiliadopatologianucleo(instance);
	}
	
	public void deleteAfiliadopatologianucleo(Afiliadopatologianucleo instance){
		dao.deleteAfiliadopatologianucleo(instance);
	}
}
