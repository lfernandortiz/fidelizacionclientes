package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Ticketredencion;

@Stateless
public class TicketredencionDaoImpl implements TicketredencionDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticketredencion> findAllTicketredencions() {
		return em.createNamedQuery("Ticketredencion.findAll").getResultList();
	}

	@Override
	public Ticketredencion obtenerTicketredencionById(Ticketredencion instance) {
		return em.find(Ticketredencion.class, instance.getIdticketredencion());
	}

	@Override
	public void insertTicketredencion(Ticketredencion instance) {
		em.persist(instance);

	}

	@Override
	public void updateTicketredencion(Ticketredencion instance) {
		em.merge(instance);

	}

	@Override
	public void deleteTicketredencion(Ticketredencion instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
