package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Permisorol;

@Stateless
public class PermisorolDaoImpl implements PermisorolDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Permisorol> findAllPermisorols() {
		return em.createNamedQuery("Permisorol.findAll").getResultList();
	}

	@Override
	public Permisorol obtenerPermisorolById(Permisorol instance) {
		return em.find(Permisorol.class, instance);
	}

	@Override
	public void insertPermisorol(Permisorol instance) {
		em.persist(instance);

	}

	@Override
	public void updatePermisorol(Permisorol instance) {
		em.merge(instance);

	}

	@Override
	public void deletePermisorol(Permisorol instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
