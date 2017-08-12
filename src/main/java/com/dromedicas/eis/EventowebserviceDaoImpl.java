package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Eventowebservice;

@Stateless
public class EventowebserviceDaoImpl implements EventowebserviceDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Eventowebservice> findAllEventowebservices() {
		return em.createNamedQuery("Eventowebservice.findAll").getResultList();
	}

	@Override
	public Eventowebservice obtenerEventowebserviceById(Eventowebservice instance) {
		 return em.find(Eventowebservice.class, instance.getIdeventowebservice());
	}

	@Override
	public void insertEventowebservice(Eventowebservice instance) {
		em.persist(instance);

	}

	@Override
	public void updateEventowebservice(Eventowebservice instance) {
		em.merge(instance);

	}

	@Override
	public void deleteEventowebservice(Eventowebservice instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
