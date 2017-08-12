package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Permisorol;
import com.dromedicas.eis.PermisorolDao;

@Stateless
public class PermisorolService {

	@EJB
	private PermisorolDao dao;
	
	public List<Permisorol> findAllPermisorols(){
		return dao.findAllPermisorols();
	}

	public Permisorol obtenerPermisorolById(Permisorol instance){
		return dao.obtenerPermisorolById(instance);
	}

	public void insertPermisorol(Permisorol instance){
		dao.insertPermisorol(instance);
	}

	public void updatePermisorol(Permisorol instance){
		dao.updatePermisorol(instance);
	}

	public void deletePermisorol(Permisorol instance){
		dao.deletePermisorol(instance);
	}
}
