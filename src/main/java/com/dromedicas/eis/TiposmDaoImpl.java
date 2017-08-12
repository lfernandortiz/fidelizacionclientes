package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Tiposm;

@Stateless
public class TiposmDaoImpl implements TiposmDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Tiposm> findAllTiposms() {
		return em.createNamedQuery("Tiposm.findAll").getResultList();
	}

	@Override
	public Tiposm obtenerTiposmById(Tiposm instance) {
		return em.find(Tiposm.class, instance.getIdtiposms());
	}

	@Override
	public void insertTiposm(Tiposm instance) {
		em.persist(instance);

	}

	@Override
	public void updateTiposm(Tiposm instance) {
		em.merge(instance);
	}

	@Override
	public void deleteTiposm(Tiposm instance) {
		em.merge(instance);
		em.remove(instance);
	}

}
