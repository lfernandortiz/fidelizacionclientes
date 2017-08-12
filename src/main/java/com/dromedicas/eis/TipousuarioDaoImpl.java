package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Tipousuario;

@Stateless
public class TipousuarioDaoImpl implements TipousuarioDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipousuario> findAllTipousuarios() {
		return em.createNamedQuery("Tipousuario.findAll").getResultList();
	}

	@Override
	public Tipousuario obtenerTipousuarioById(Tipousuario instance) {
		return em.find(Tipousuario.class, instance.getIdtipousuario());
	}
	
	@Override
	public Tipousuario obtenerTipousuarioById(int id) {
		return em.find(Tipousuario.class, id);
	}


	@Override
	public void insertTipousuario(Tipousuario instance) {
		em.persist(instance);

	}

	@Override
	public void updateTipousuario(Tipousuario instance) {
		em.merge(instance);

	}

	@Override
	public void deleteTipousuario(Tipousuario instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
