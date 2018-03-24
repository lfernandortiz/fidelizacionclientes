package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Smsenvio;

@Stateless
public class SmsenvioDaoImpl implements SmsenvioDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Smsenvio> findAllSmsenvios() {
		return em.createNamedQuery("Smsenvio.findAll").getResultList();
	}

	@Override
	public Smsenvio obtenerSmsenvioById(Smsenvio instance) {
		return em.find(Smsenvio.class, instance.getIdsmsenvio());
	}

	@Override
	public void insertSmsenvio(Smsenvio instance) {
		em.persist(instance);

	}

	@Override
	public Integer updateSmsenvio(Smsenvio instance) {
		Smsenvio sms = em.merge(instance);
		return sms.getIdsmsenvio();
		
	}

	@Override
	public void deleteSmsenvio(Smsenvio instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
