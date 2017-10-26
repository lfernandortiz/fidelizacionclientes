package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Transaccion;
import com.dromedicas.eis.TransaccionDao;

@Stateless
public class TransaccionService {
	
	@EJB
	TransaccionDao dao;
	
	public List<Transaccion> findAllTransaccions(){
		return dao.findAllTransaccions();
	}

	public Transaccion obtenerTransaccionById(Transaccion instance){
		return dao.obtenerTransaccionById(instance);
	}

	public void insertTransaccion(Transaccion instance){
		dao.insertTransaccion(instance);
	}

	public void updateTransaccion(Transaccion instance){
		dao.updateTransaccion(instance);
	}

	public void deleteTransaccion(Transaccion instance){
		dao.deleteTransaccion(instance);
	}

}
