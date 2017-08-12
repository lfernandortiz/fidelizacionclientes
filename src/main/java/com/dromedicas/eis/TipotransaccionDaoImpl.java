package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Tipotransaccion;

@Stateless
public class TipotransaccionDaoImpl implements TipotransaccionDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipotransaccion> findAllTipotransaccions() {
		return em.createNamedQuery("Tipotransaccion.findAll").getResultList();
	}

	@Override
	public Tipotransaccion obtenerTipotransaccionById(Tipotransaccion instance) {
		return em.find(Tipotransaccion.class, instance.getIdtipotransaccion());
	}

	@Override
	public void insertTipotransaccion(Tipotransaccion instance) {
		em.persist(instance);

	}

	@Override
	public void updateTipotransaccion(Tipotransaccion instance) {
		em.merge(instance);

	}

	@Override
	public void deleteTipotransaccion(Tipotransaccion instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
