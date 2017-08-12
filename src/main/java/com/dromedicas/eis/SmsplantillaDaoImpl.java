package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Smsplantilla;

@Stateless
public class SmsplantillaDaoImpl implements SmsplantillaDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;	

	@SuppressWarnings("unchecked")
	@Override
	public List<Smsplantilla> findAllSmsplantillas() {
		return em.createNamedQuery("Smsplantilla.findAll").getResultList();
	}

	@Override
	public Smsplantilla obtenerSmsplantillaById(Smsplantilla instance) {
		return em.find(Smsplantilla.class, instance.getIdsmsplantilla());
	}

	@Override
	public void insertSmsplantilla(Smsplantilla instance) {
		em.persist(instance);

	}

	@Override
	public void updateSmsplantilla(Smsplantilla instance) {
		em.merge(instance);

	}

	@Override
	public void deleteSmsplantilla(Smsplantilla instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
