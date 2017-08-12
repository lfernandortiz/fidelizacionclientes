package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Sucursal;

@Stateless
public class SucursalDaoImpl implements SucursalDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Sucursal> findAllSucursals() {
		return em.createNamedQuery("Sucursal.findAll").getResultList();
	}

	@Override
	public Sucursal obtenerSucursalById(String id) {
		return em.find(Sucursal.class, Integer.valueOf(id));
	}
	
	@Override
	public Sucursal obtenerSucursalById(Sucursal instance) {
		return em.find(Sucursal.class, instance.getIdsucursal());
	}

	@Override
	public void insertSucursal(Sucursal instance) {
		em.persist(instance);
	}

	@Override
	public void updateSucursal(Sucursal instance) {
		em.merge(instance);

	}

	@Override
	public void deleteSucursal(Sucursal instance) {
	    instance =  em.merge(instance);		
		em.remove(instance);

	}

}
