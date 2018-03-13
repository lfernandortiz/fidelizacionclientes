package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Campania;

@Stateless
public class CampaniaDaoImpl implements CampaniaDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Campania> findAllCampanias() {
		return em.createNamedQuery("SELECT c FROM Campania c").getResultList();
	}

	@Override
	public Campania obtenerCampaniaById(Campania instance) {
		return em.find(Campania.class, instance.getIdcampania());
	}
	
	@Override
	public void insertCampania(Campania instance) {
		em.persist(instance);

	}

	@Override
	public Integer updateCampania(Campania instance) {
		Campania cTemp = em.merge(instance);		
		return cTemp.getIdcampania();

	}
																							
	@Override
	public void deleteCampania(Campania instance) {
		em.merge(instance);
		em.remove(instance);
	}
	

}
