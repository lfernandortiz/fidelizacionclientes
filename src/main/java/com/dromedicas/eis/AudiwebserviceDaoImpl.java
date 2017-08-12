package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Audiwebservice;

@Stateless
public class AudiwebserviceDaoImpl implements AudiwebserviceDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Audiwebservice> findAllAudiwebservices() {
		return em.createNamedQuery("Audiwebservice.findAll").getResultList();

	}

	@Override
	public Audiwebservice obtenerAudiwebserviceById(Audiwebservice instance) {
		return em.find(Audiwebservice.class, instance.getIdaudiwebservice());
	}

	
	@Override
	public void insertAudiwebservice(Audiwebservice instance) {
		em.persist(instance);
	}

	@Override
	public void updateAudiwebservice(Audiwebservice instance) {
		em.merge(instance);
	}

	@Override
	public void deleteAudiwebservice(Audiwebservice instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
