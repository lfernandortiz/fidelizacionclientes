package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Usuarioperfil;

@Stateless
public class UsuarioperfilDaoImpl implements UsuarioperfilDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuarioperfil> findAllUsuarioperfils() {
		return em.createNamedQuery("Usuarioperfil.findAll").getResultList();
	}

	@Override
	public Usuarioperfil obtenerUsuarioperfilById(Usuarioperfil instance) {
		return em.find(Usuarioperfil.class, instance.getId());
	}

	@Override
	public void insertUsuarioperfil(Usuarioperfil instance) {
		em.persist(instance);

	}

	@Override
	public void updateUsuarioperfil(Usuarioperfil instance) {
		em.merge(instance);

	}

	@Override
	public void deleteUsuarioperfil(Usuarioperfil instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
