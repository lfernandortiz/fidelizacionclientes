package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Rol;

@Stateless
public class RolDaoImpl implements RolDao {
	
	@PersistenceContext(name="PuntoFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> findAllRols() {
		return em.createNamedQuery("Rol.findAll").getResultList();
	}

	@Override
	public Rol obtenerRolById(Rol instance) {
		return em.find(Rol.class, instance.getIdrol());
	}

	@Override
	public void insertRol(Rol instance) {
		em.persist(instance);

	}

	@Override
	public void updateRol(Rol instance) {
		em.merge(instance);

	}

	@Override
	public void deleteRol(Rol instance) {
		em.merge(instance);
		em.remove(instance);

	}

	@Override
	public Rol obtenerRolById(String id) {
		return em.find(Rol.class, id);		
	}

}
