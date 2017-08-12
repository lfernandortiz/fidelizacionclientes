package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Punto;

@Stateless
public class PuntoDaoImpl implements PuntoDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Punto> findAllPuntos() {
		return em.createNamedQuery("Punto.findAll").getResultList();
	}

	@Override
	public Punto obtenerPuntoById(Punto instance) {
		return em.find(Punto.class, instance.getIdpuntos());
	}

	@Override
	public void insertPunto(Punto instance) {
		em.persist(instance);

	}

	@Override
	public void updatePunto(Punto instance) {
		em.merge(instance);

	}

	@Override
	public void deletePunto(Punto instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
