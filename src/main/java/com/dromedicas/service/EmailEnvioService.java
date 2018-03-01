package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Emailenvio;
import com.dromedicas.eis.EmailenvioDao;

@Stateless
public class EmailEnvioService {
	
	@EJB
	private EmailenvioDao dao;
	
	
	public List<Emailenvio> findAllEmailenvios() {
		return dao.findAllEmailenvios();
	}

	
	public Emailenvio obtenerEmailenvioById(Emailenvio instance) {
		return dao.obtenerEmailenvioById(instance);
	}

	
	public void insertEmailenvio(Emailenvio instance) {
		dao.insertEmailenvio(instance);

	}

	
	public void updateEmailenvio(Emailenvio instance) {
		dao.updateEmailenvio(instance);

	}

	
	public void deleteEmailenvio(Emailenvio instance) {
		dao.deleteEmailenvio(instance);
	}


}
