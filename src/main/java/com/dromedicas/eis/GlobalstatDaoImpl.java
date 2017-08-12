package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Globalstat;

@Stateless
public class GlobalstatDaoImpl implements GlobalstatDao {
	
	@PersistenceContext(name="PunrtosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Globalstat> findAllGlobalstats() {
		return em.createNamedQuery("Globalstat.findAll").getResultList();
	}

	@Override
	public Globalstat obtenerGlobalstatById(Globalstat instance) {
		return em.find(Globalstat.class, instance.getIdglobalstats());
	}

	@Override
	public void insertGlobalstat(Globalstat instance) {
		em.persist(instance);

	}

	@Override
	public void updateGlobalstat(Globalstat instance) {
		em.merge(instance);

	}

	@Override
	public void deleteGlobalstat(Globalstat instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
