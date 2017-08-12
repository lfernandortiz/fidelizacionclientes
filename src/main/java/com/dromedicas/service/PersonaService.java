package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.eis.AfiliadoDao;

@Stateless
public class PersonaService {
	
	@EJB
	AfiliadoDao afDao;
	
	public List<Afiliado> findAllAfiliados() {
		return afDao.findAllAfiliados();
				
	}

	
	public Afiliado obtenerAfiliadoById(Afiliado instance) {
		return afDao.obtenerAfiliadoById(instance);
	}

	
	public Afiliado obtenerAfiliadoByDocumento(Afiliado instance) {
		return afDao.obtenerAfiliadoByDocumento(instance);
	}

	
	public void insertAfiliado(Afiliado instance) {
		afDao.insertAfiliado(instance);
	}

	
	public void updateAfiliado(Afiliado instance) {
		afDao.updateAfiliado(instance);
	}

	
	public void deleteAfiliado(Afiliado instance) {
		afDao.deleteAfiliado(instance);
	}

}
