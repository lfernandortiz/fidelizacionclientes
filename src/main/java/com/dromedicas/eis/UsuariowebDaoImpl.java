package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Usuarioweb;

@Stateless
public class UsuariowebDaoImpl implements UsuariowebDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuarioweb> findAllUsuariowebs() {
		return em.createNamedQuery("Usuarioweb.findAll").getResultList();
	}

	@Override
	public Usuarioweb obtenerUsuariowebById(Usuarioweb instance) {
		return em.find(Usuarioweb.class, instance.getIdusuarioweb());
	}

	@Override
	public void insertUsuarioweb(Usuarioweb instance) {
		em.persist(instance);

	}

	@Override
	public void updateUsuarioweb(Usuarioweb instance) {
		em.merge(instance);

	}

	@Override
	public void deleteUsuarioweb(Usuarioweb instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
