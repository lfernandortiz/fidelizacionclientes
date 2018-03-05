package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Smsenvio;
import com.dromedicas.eis.SmsenvioDao;


@Stateless
public class SmsEnvioService {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@EJB
	private SmsenvioDao dao;
	
	
	public List<Smsenvio> findAllSmsenvios() {
		return dao.findAllSmsenvios();
	}

	
	public Smsenvio obtenerSmsenvioById(Smsenvio instance) {
		return dao.obtenerSmsenvioById(instance);
	}

	
	public void insertSmsenvio(Smsenvio instance) {
		dao.insertSmsenvio(instance);

	}

	
	public void updateSmsenvio(Smsenvio instance) {
		dao.updateSmsenvio(instance);

	}

	
	public void deleteSmsenvio(Smsenvio instance) {
		dao.deleteSmsenvio(instance);

	}
	

}
