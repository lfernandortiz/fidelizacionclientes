package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Detransaccion;

@Stateless
public class DetransaccionDaoImpl implements DetransaccionDao {
	
	@PersistenceContext(name="FarmaFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Detransaccion> findAllDetransaccions() {
		return em.createNamedQuery("Detransaccion.findAll").getResultList();
	}

	@Override
	public Detransaccion obtenerDetransaccionById(Detransaccion instance) {
		return em.find(Detransaccion.class, instance.getIddetransaccion());
	}

	@Override
	public void insertDetransaccion(Detransaccion instance) {
		em.persist(instance);

	}

	@Override
	public void updateDetransaccion(Detransaccion instance) {
		em.merge(instance);

	}

	@Override
	public void deleteDetransaccion(Detransaccion instance) {
		em.merge(instance);
		em.remove(instance);
	}

}
