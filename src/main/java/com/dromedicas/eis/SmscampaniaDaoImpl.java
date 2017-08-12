package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Smscampania;

@Stateless
public class SmscampaniaDaoImpl implements SmscampaniaDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Smscampania> findAllSmscampanias() {
		return em.createNamedQuery("Smscampania.findAll").getResultList();
	}

	@Override
	public Smscampania obtenerSmscampaniaById(Smscampania instance) {
		return em.find(Smscampania.class, instance.getId());
	}

	@Override
	public void insertSmscampania(Smscampania instance) {
		em.persist(instance);

	}

	@Override
	public void updateSmscampania(Smscampania instance) {
		em.merge(instance);

	}

	@Override
	public void deleteSmscampania(Smscampania instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
