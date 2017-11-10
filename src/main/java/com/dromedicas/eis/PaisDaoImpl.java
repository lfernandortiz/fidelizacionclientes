package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.dromedicas.domain.Pais;


@Stateless
public class PaisDaoImpl implements PaisDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Pais> findAllPais() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Pais.findAll").getResultList();				
	}

	@Override
	public Pais obtenerPaisById(Pais instance) {
		// TODO Auto-generated method stub
		return em.find(Pais.class, instance.getIdpaises());
	}
	
	@Override
	public void insertPais(Pais instance) {
		em.persist(instance);
	}

	@Override
	public void updatePais(Pais instance) {
		em.merge(instance);
	}

	@Override
	public void deletePais(Pais instance) {
		em.merge(instance);
		em.remove(instance);
	}
	

}
