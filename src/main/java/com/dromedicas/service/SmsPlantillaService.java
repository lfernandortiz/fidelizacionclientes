package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Smsplantilla;
import com.dromedicas.eis.SmsplantillaDao;

@Stateless
public class SmsPlantillaService implements Serializable {
	
	@EJB
	private SmsplantillaDao dao;
	
	public List<Smsplantilla> findAllSmsplantillas(){
		return dao.findAllSmsplantillas();	
	}

	public Smsplantilla obtenerSmsplantillaById(Smsplantilla instance){
		return dao.obtenerSmsplantillaById(instance);
	}

	public void insertSmsplantilla(Smsplantilla instance){
		dao.insertSmsplantilla(instance);
	}

	public void updateSmsplantilla(Smsplantilla instance){
		dao.updateSmsplantilla(instance);
	}

	public void deleteSmsplantilla(Smsplantilla instance){
		dao.deleteSmsplantilla(instance);
	}

	
	

}
