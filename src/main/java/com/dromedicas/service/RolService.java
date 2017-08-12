package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Rol;
import com.dromedicas.eis.RolDao;

@Stateless
public class RolService {

	@EJB
	private RolDao dao;
	
	public List<Rol> findAllRols(){
		return dao.findAllRols();
	}

	public Rol obtenerRolById(Rol instance){
		return dao.obtenerRolById(instance);
	}
	
	public Rol obtenerRolById(String id){
		return dao.obtenerRolById(id);
	}
	

	public void insertRol(Rol instance){
		dao.insertRol(instance);
	}

	public void updateRol(Rol instance){
		dao.updateRol(instance);
	}

	public void deleteRol(Rol instance){
		dao.deleteRol(instance);
	}
	
	
}
