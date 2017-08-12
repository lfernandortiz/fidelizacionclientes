package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Perfil;

@Stateless
public class PerfilDaoImpl implements PerfilDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Perfil> findAllPerfils() {
		return em.createNamedQuery("Perfil.findAll").getResultList();
	}

	@Override
	public Perfil obtenerPerfilById(Perfil instance) {
		return em.find(Perfil.class, instance);
	}

	@Override
	public void insertPerfil(Perfil instance) {
		em.persist(instance);

	}

	@Override
	public void updatePerfil(Perfil instance) {
		em.merge(instance);

	}

	@Override
	public void deletePerfil(Perfil instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
