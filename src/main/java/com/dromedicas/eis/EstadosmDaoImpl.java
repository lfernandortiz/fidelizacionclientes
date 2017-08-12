package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Estadosm;

@Stateless
public class EstadosmDaoImpl implements EstadosmDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Estadosm> findAllEstadosms() {
		return em.createNamedQuery("Estadosm.findAll").getResultList();
	}

	@Override
	public Estadosm obtenerEstadosmById(Estadosm instance) {
		return em.find(Estadosm.class, instance.getIdestadosms());
	}

	@Override
	public void insertEstadosm(Estadosm instance) {
		em.persist(instance);

	}

	@Override
	public void updateEstadosm(Estadosm instance) {
		em.merge(instance);

	}

	@Override
	public void deleteEstadosm(Estadosm instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
