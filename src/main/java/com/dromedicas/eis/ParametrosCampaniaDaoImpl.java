package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Paremetroscampania;

@Stateless
public class ParametrosCampaniaDaoImpl implements ParametrosCampaniaDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@Override
	public List<Paremetroscampania> findAllParemetroscampanias() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Paremetroscampania.findAll").getResultList();			
	}

	@Override
	public Paremetroscampania obtenerParemetroscampaniaById(Paremetroscampania instance) {
		// TODO Auto-generated method stub
		return em.find(Paremetroscampania.class, instance.getIdparemetroscampania() );
	}

	@Override
	public void insertParemetroscampania(Paremetroscampania instance) {
		em.persist(instance);
		
	}

	@Override
	public void updateParemetroscampania(Paremetroscampania instance) {
		em.merge(instance);
		
	}

	@Override
	public void deleteParemetroscampania(Paremetroscampania instance) {
		em.merge(instance);
		em.remove(instance);
		
	}

	
}
