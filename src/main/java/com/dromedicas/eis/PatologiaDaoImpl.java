package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Patologia;

@Stateless
public class PatologiaDaoImpl implements PatologiaDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Patologia> findAllPatologias() {
		// TODO Auto-generated method stub		
		return em.createNamedQuery("Patologia.findAll").getResultList();
	}

	@Override
	public Patologia obtenerPatologiaById(Patologia instance) {
		// TODO Auto-generated method stub
		return em.find(Patologia.class, instance.getIdpatologia());
	}

	@Override
	public void insertPatologia(Patologia instance) {
		em.persist(instance);

	}

	@Override
	public void updatePatologia(Patologia instance) {
		em.merge(instance);

	}

	@Override
	public void deletePatologia(Patologia instance) {
		em.merge(instance);
		//em.remove(instance);
		
		em.remove(em.contains(instance) ? instance : em.merge(instance));

	}

}
